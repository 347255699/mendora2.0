package org.mendora.kernel.scanner.route;

import com.google.inject.Inject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.mendora.kernel.properties.SysConfig;

/**
 * created by:xmf
 * date:2018/3/21
 * description:
 */
@Slf4j
public class AbstractRoute {
    @Inject
    protected Vertx vertx;
    @Inject
    protected SysConfig config;
    @Inject
    protected Router router;
    @Inject
    protected WebResult webResult;

    public void route(String prefix) {
    }
}
