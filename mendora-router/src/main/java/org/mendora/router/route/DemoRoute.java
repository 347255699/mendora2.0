package org.mendora.router.route;

import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.mendora.kernel.scanner.route.AbstractRoute;
import org.mendora.kernel.scanner.route.RequestRouting;
import org.mendora.kernel.scanner.route.Route;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
@Route("/mendora/demo")
public class DemoRoute extends AbstractRoute {

    @RequestRouting(path = "", method = HttpMethod.GET)
    public void demo(RoutingContext rc) {
        rc.response().end("<h1>Just a test demo.</h1>");
    }

}
