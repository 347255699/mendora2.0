package org.mendora.web.route;

import com.google.inject.Inject;
import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.mendora.facade.data.mongo.rxjava.MongoAccesser;
import org.mendora.kernel.scanner.route.AbstractRoute;
import org.mendora.kernel.scanner.route.RequestRouting;
import org.mendora.kernel.scanner.route.Route;
import org.mendora.kernel.scanner.route.WebAuth;
import org.mendora.util.constant.MongoCol;
import org.mendora.util.generate.MongoAdapter;
import org.mendora.util.result.JsonResult;

/**
 * created by:xmf
 * date:2018/3/7
 * description:
 */
@Route("/mendora/demo")
public class DemoRoute extends AbstractRoute {
    @Inject
    private WebAuth webAuth;
    @Inject
    private MongoAccesser mongoAccesser;

    @RequestRouting(path = "", method = HttpMethod.GET)
    public void demo(RoutingContext rc) {
        mongoAccesser.rxFind(MongoAdapter.find(MongoCol.COL_AP, JsonResult.empty()))
                .subscribe(reply -> webResult.consume(reply, rc),
                        err -> webResult.fail(err, rc));
    }

}
