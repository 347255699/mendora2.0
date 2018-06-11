package org.mendora.kernel.scanner.verticle;

import com.google.inject.Inject;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.scanner.route.RouteScanner;

/**
 * Created by kam on 2018/5/5.
 */
public class WebVerticle extends DefaultVerticle {
    @Inject
    private Router router;

    @Override
    public void start() throws Exception {

        // scanning route
        RouteScanner scanner = injector.getInstance(RouteScanner.class);
        scanner.scan(config.property(Const.WEB_ROUTE_PACKAGE), injector);
        // use http request logging.
        router.route().handler(LoggerHandler.create(LoggerFormat.TINY));
        // use http request body as Json,Buffer,String.
        long bodyLimit = Long.parseLong(config.property(Const.WEB_REQUEST_BODY_SIZE));
        router.route("/static/*").handler(StaticHandler.create().setWebRoot("aider"));
        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit));

    }
}
