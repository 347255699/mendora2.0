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
		        -aider 启动辅助资源，api文档服务等
			 
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