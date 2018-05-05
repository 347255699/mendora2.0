package org.mendora.verticles;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.core.json.JsonArray;
import io.vertx.rxjava.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.mendora.properties.Const;
import org.mendora.properties.SysConfig;
import org.mendora.scanner.base.PackageScannerImpl;
import org.mendora.util.result.JsonResult;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class VerticleScanner {
    private static final String DEPLOY_ID = "dId";
    private static final String VERTICLE_NAME = "vName";
    @Inject
    private SysConfig configHolder;
    @Inject
    private Vertx vertx;

    /**
     * scanning verticles
     */
    public void scan(String packagePath, Injector injector) {
        try {
            List<String> names = new PackageScannerImpl<DefaultVerticle>(packagePath, injector.getInstance(ClassLoader.class))
                    .classNames(DefaultVerticle.class.getName(), this.getClass().getName());
            log.info(String.valueOf(names.size()));
            val storage = new JsonArray(new ArrayList(names.size()));
            Observable.from(names)
                    .flatMap(name -> {
                        try {
                            DefaultVerticle dv = (DefaultVerticle) injector.getInstance(Class.forName(name));
                            dv.setInjector(injector);
                            return Observable.just(dv);
                        } catch (ClassNotFoundException e) {
                            return Observable.error(e);
                        }
                    })
                    .subscribe(v -> deploy(v, storage),
                            err -> log.error(err.getMessage()),
                            () -> {
                                log.info("all the \"verticles\" deployed");
                                configHolder.setProperty(Const.VERTICLE_STORAGE_KEY, storage);
                            });
            names.forEach(log::info);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * deploy verticles and record info.
     *
     * @param verticle
     * @param storage
     */
    private void deploy(DefaultVerticle verticle, JsonArray storage) {
        vertx.getDelegate().deployVerticle(verticle, verticle.options(), res -> {
            if (res.succeeded()) {
                String dId = res.result();
                String vName = verticle.getClass().getName();
                storage.add(JsonResult.two().put(DEPLOY_ID, dId).put(VERTICLE_NAME, vName));
            } else {
                log.error(res.cause().getMessage());
            }
        });
    }
}
