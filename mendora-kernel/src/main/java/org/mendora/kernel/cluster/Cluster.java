package org.mendora.kernel.cluster;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.hazelcast.config.*;
import io.vertx.core.VertxOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.mendora.kernel.binder.VertxBinder;
import org.mendora.kernel.binder.WebBinder;
import org.mendora.kernel.config.KernelConfig;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.properties.SysConfig;
import org.mendora.kernel.scanner.verticle.DefaultVerticle;
import org.mendora.kernel.scanner.verticle.VerticleScanner;
import org.mendora.kernel.scanner.verticle.WebVerticle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
public class Cluster {
    /**
     * launching Vertx micro kernel and deploy verticle.
     */
    public static void launch(Injector injector) {
        SysConfig sysConfig = injector.getInstance(SysConfig.class);
        /** hazelcast configuration **/
        Config config = new Config()
                .setProperty(Const.HAZELCAST_LOGGING_TYPE, sysConfig.property(Const.HAZELCAST_LOGGING_TYPE))
                .setProperty(Const.HAZELCAST_HEARBEAT_INTERVAL_SECONDS, sysConfig.property(Const.HAZELCAST_HEARBEAT_INTERVAL_SECONDS));
        TcpIpConfig tcpIpConfig = new TcpIpConfig()
                .setEnabled(true)
                .setMembers(Arrays.asList(sysConfig.property(Const.CLUSTER_SERVER_IPS).split(",")));
        JoinConfig joinConfig = new JoinConfig()
                .setMulticastConfig(new MulticastConfig().setEnabled(false))
                .setTcpIpConfig(tcpIpConfig);
        NetworkConfig networkConfig = new NetworkConfig()
                .setPort(Integer.parseInt(sysConfig.property(Const.CLUSTER_PORT)))
                .setJoin(joinConfig);
        config.setNetworkConfig(networkConfig);

        // configuration and launching Vertx cluster.
        VertxOptions options = new VertxOptions()
                .setClusterManager(new HazelcastClusterManager(config));
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Injector subInjector = injector.createChildInjector(binderList(res.result(), injector));
                deployVerticle(subInjector);
            }
        });
    }

    private static void deployVerticle(Injector injector) {
        KernelConfig kernelConfig = injector.getInstance(KernelConfig.class);
        SysConfig sysConfig = injector.getInstance(SysConfig.class);
        Vertx vertx = injector.getInstance(Vertx.class);
        if (kernelConfig.isSanVerticle()) {
            VerticleScanner scanner = injector.getInstance(VerticleScanner.class);
            scanner.scan(sysConfig.property(Const.VERTICLE_PACKAGE), injector);
        }
        switch (kernelConfig.getMicroService()) {
            case WEB:
                DefaultVerticle dv = injector.getInstance(WebVerticle.class);
                dv.setInjector(injector);
                vertx.getDelegate().deployVerticle(dv);
                break;
        }

    }

    private static List<AbstractModule> binderList(Vertx vertx, Injector injector) {
        List<AbstractModule> binders = new ArrayList<>();
        binders.add(new VertxBinder(vertx));
        KernelConfig config = injector.getInstance(KernelConfig.class);
        switch (config.getMicroService()) {
            case WEB:
                binders.add(new WebBinder(Router.router(vertx)));
                break;
        }
        return binders;
    }
}
