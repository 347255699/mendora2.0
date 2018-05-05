package org.mendora.kernel.cluster;

import com.google.inject.Injector;
import com.hazelcast.config.*;
import io.vertx.core.VertxOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.mendora.kernel.binder.VertxBinder;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.properties.SysConfig;
import org.mendora.kernel.scanner.verticle.VerticleScanner;

import java.util.Arrays;

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
        ClassLoader classLoader = injector.getInstance(ClassLoader.class);
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
                Vertx vertx = res.result();
                Injector subInjector = injector.createChildInjector(new VertxBinder(vertx));
                VerticleScanner scanner = subInjector.getInstance(VerticleScanner.class);
                scanner.scan(sysConfig.property(Const.VERTICLE_PACKAGE), subInjector);
            }
        });
    }
}
