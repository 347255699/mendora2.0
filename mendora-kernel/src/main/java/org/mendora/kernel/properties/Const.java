package org.mendora.kernel.properties;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
public interface Const {
    // base
    String ROOT_PATH = "root.path";
    String LOGGER_FACTORY_CLASS = "logger.factory.class";
    String LOGGER_CONFIG_PATH = "logger.config.path";
    String VERTICLE_STORAGE_KEY = "verticle.storage.key";
    String AVAILABLE_PROCESSORS = "available.processors";
    String VERTICLE_PACKAGE = "verticle.package";
    String SERVICE_PROVIDER_PACKAGE = "service.provider.package";
    // hazelcast
    String HAZELCAST_LOGGING_TYPE = "hazelcast.logging.type";
    String HAZELCAST_HEARBEAT_INTERVAL_SECONDS = "hazelcast.heartbeat.interval.seconds";
    String CLUSTER_PORT = "cluster.port";
    String CLUSTER_SERVER_IPS = "cluster.server.ips";
    // web
    String WEB_LISTEN_PORT = "web.listen.port";
    String WEB_ROUTE_PACKAGE = "web.route.package";
    String WEB_REQUEST_BODY_SIZE="web.request.body.size";
}
