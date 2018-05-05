package org.mendora.router.verticle;

import com.google.inject.Injector;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import org.mendora.kernel.binder.RouterBinder;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.scanner.route.RouteScanner;
import org.mendora.kernel.scanner.verticle.DefaultVerticle;

/**
 * Created by kam on 2018/5/5.
 */
public class RouterVerticle extends DefaultVerticle{
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        Injector subInjector = injector.createChildInjector(new RouterBinder(router));
        // scanning route
        RouteScanner scanner = subInjector.getInstance(RouteScanner.class);
        scanner.scan(config.property(Const.WEB_ROUTE_PACKAGE), subInjector);
        // use http request logging.
        router.route().handler(LoggerHandler.create(LoggerFormat.TINY));
        // use http request body as Json,Buffer,String.
        long bodyLimit = Long.parseLong(config.property(Const.WEB_REQUEST_BODY_SIZE));
        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit));
    }
}
