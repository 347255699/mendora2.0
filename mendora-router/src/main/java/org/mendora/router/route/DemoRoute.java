package org.mendora.router.route;

import com.google.inject.Inject;
import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.mendora.kernel.scanner.route.AbstractRoute;
import org.mendora.kernel.scanner.route.RequestRouting;
import org.mendora.kernel.scanner.route.Route;
import org.mendora.kernel.scanner.route.WebAuth;
import org.mendora.util.result.JsonResult;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Slf4j
@Route("/mendora/demo")
public class DemoRoute extends AbstractRoute {
    @Inject
    private WebAuth webAuth;
    @RequestRouting(path = "", method = HttpMethod.GET)
    public void demo(RoutingContext rc) {
        rc.response().end(webAuth.issueJWToken(JsonResult.empty()));
    }

}
