# 说明文档
## 简介
该版本在mendora第一个版本的基础上精简代码，以及服务基础功能内核化。任何一个微服务仅需要引入kernel包，按照微服务定制内核功能即可快速构建一个微服务。

## 技术方案
核心体系为Vertx Java、Guice和Lombok。详情如下列表所示：

1. guice：ioc容器，依赖注入
2. lombok：简化代码
3. slf4j-log4j12：日志记录  
	a. log4j：日志记录实现
4. commons-lang3：第三方工具包
5. Vertx Java  
	a. vertx-web：创建http服务器，http请求路由  
	b. vertx-hazelcase：集群管理器  
	&nbsp;&nbsp;&nbsp;&nbsp;1) hazelcast：集群管理器实现  
	c. vertx-auth-jwt：http认证授权服务  
	d. vertx-rx-java: rx化api实现  
	e. vertx-codegen：服务代理代码生成器，与vertx-service-proxy一起使用  
	f. vertx-service-proxy：服务代理实现  
	g. vertx-mysql-postgresql-client：postgre/mysql客户端  
	h. vertx-mongo-client：mongo客户端
> note：集成版本可查看项目.pom文件properties节点
## 体系结构
共划分为七个模块。分别为三个服务包，aider、web、rear。三个基础包，kernel、util和facade。一个代码生成包generate。  
基础包依赖关系：kernel -> facade -> util    
各包的详细作用可参见下表：

	-root
		-kernel 集成了guice ioc容器的集群服务内核
		    -binder ioc容器绑定类
		    -client 客户端加载器
		    -cluster 集群启动器
		    -config 内核相关配置文件
		    -properties 基础配置信息
		    -scanner 扫描器
		    -KernelLauncher.java 内核启动入口
	
		-web 提供web服务，系统controller层载体
		    -java
		        -route route包，controller层主要业务包，注意模块划分
		        -ApplicationMain.java 服务入口
		    -resources
		        -aider 系统辅助资源，api文档服务等
			 
		-facade rpc服务接口
		    -java
		        -annotation 服务接口相关注解
		        -data 数据库访问接口
	
		-rear rpc服务实现载体
		    -accesser 数据库访问实现
		    -ApplicationMain.java 服务入口
	
		-util 全局工具包，严格划分系统工具作用域
		    -constant 常量
		    -generate 生成工具
		    -result 结果包
	
		-aider 系统辅助服务载体
		    -java
		        -route route包，controller层主要业务包，注意模块划分
		        -verticle verticle包
		        -ApplicationMain.java 服务入口
		    -resources
		        -aider 系统辅助资源，api文档服务等
		    
		-generate 代码生成辅助模块
		    -generated 代码生成输出目录
		    -java 接口置放处
## 实践
### step1:引包
引入内核kernel包。
```xml
     <dependency>
        <groupId>org.mendora</groupId>
        <artifactId>mendora-kernel</artifactId>
        <version>${common.version}</version>
     </dependency>
```
### step2:编辑配置文件
默认加载`resources`目录下`config`文件夹中的配置文件。即加载路径为`classpath:config/config.properties`。  
包含`log4j.properties`。若为`web`服务需要额外添加`keystore.jceks`以支持JWT校验。
#### web服务配置清单
```properties
# base properties
logger.factory.class=io.vertx.core.logging.SLF4JLogDelegateFactory
### where your logger properties? ###
logger.config.path=/config/log4j.properties
### where your verticle group? ###
verticle.package=org.mendora.web.verticle
## for web
web.listen.port=8080
## for hazelcast
hazelcast.logging.type=slf4j
hazelcast.heartbeat.interval.seconds=15
cluster.port=5701
cluster.server.ips=127.0.0.1
## for facade
# where your facade group?
facade.package=org.mendora.facade.data

# for web module
## for web
### where your route group? ###
web.route.package=org.mendora.web.route
web.request.body.size=2048576
## for jwt
web.jwt.key.passwd=menfre
web.jwt.issuer=mendora
web.jwt.expires.minutes=30
## for cors
cors.allowed.methods=GET,POST,PUT,DELETE,PATCH,OPTIONS
cors.allowed.headers=Content-Type,Content-Length,Authorization,Accept,X-Requested-With
cors.max.age.seconds=3600
```
#### rear服务配置清单
```properties
# for base module
logger.factory.class=io.vertx.core.logging.SLF4JLogDelegateFactory
### where your logger properties? ###
logger.config.path=/config/log4j.properties
### where your verticle group? ###
verticle.package=org.mendora.rear.verticle
### where your service provider group? ###
provider.package=org.mendora.rear
## for hazelcast
hazelcast.logging.type=slf4j
hazelcast.heartbeat.interval.seconds=15
cluster.port=5701
cluster.server.ips=127.0.0.1
## for facade
facade.package=org.mendora.facade.data

# for rear module
## for postgre
data.db.postgre.uri=postgre://<account>:<password>@<host>:<port>/<dbName>\
  ?maxPoolSize=10&charset=UTF-8&queryTimeout=3000
## for mongo
data.db.mongo.uri=mongodb://<account>:<password>@<host>:<port>/<dbName>\
  ?waitqueuemultiple=20000&maxPoolSize=30&minPoolSize=5
```
### step3:配置和启动内核
在main方法中配置内核信息和定制行为。如启动web服务。
```java
    URL location = ApplicationMain.class.getProtectionDomain().getCodeSource().getLocation();
    KernelConfig config = new KernelConfig();
    // default ClassLoader
    config.setClassLoader(ApplicationMain.class.getClassLoader());
    // root path
    config.setRootUrl(location);
    // micro service type.
    config.setMicroService(MicroService.WEB);
    // want to scann verticle group?
    config.setScanVerticle(false);
    // want to scann facade group?
    config.setScanFacade(true);
    // setting aop entry group.
    List<AopEntry> aopEntries = new ArrayList<>();
    aopEntries.add(new AopEntry(Monitor.class, new MonitorMethodInterceptor()));
    config.setAopEntries(aopEntries);
    // launching now baby!
    KernelLauncher.launch(config);
```
## Swagger UI
默认在we服务中集成swagger ui和editor。  
可以通过`http://<host>:<port>/static/ui/index.html`和`http://<host>:<port>/static/editor/index.html`来访问API文档已经编辑器。

