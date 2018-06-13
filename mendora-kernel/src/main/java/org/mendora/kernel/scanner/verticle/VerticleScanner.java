package org.mendora.kernel.scanner.verticle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.properties.SysConfig;
import org.mendora.kernel.scanner.base.PackageScannerImpl;
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
    private SysConfig config;
    private Vertx vertx;

    @Inject
    public VerticleScanner(SysConfig config, Vertx vertx) {
        this.config = config;
        this.vertx = vertx;
    }

    /**
     * scanning verticle
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
                                log.info("all the \"verticle\" deployed");
                                config.setProperty(Const.VERTICLE_STORAGE_KEY, storage);
                            });
            names.forEach(log::info);
        } catch (Exception e) {
            Throwable e0=e.getCause();
            if(e0!=null) {
                log.error(e0.getClass().getName() + "==>" + e0.getStackTrace()[0].toString());
            }else {
                log.error("nocause："+e.getStackTrace()[0].toString());
            }
        }
    }

    /**
     * deploy verticle and record info.
     *
     * @param verticle
     * @param storage
     */
    private void deploy(DefaultVerticle verticle, JsonArray storage) {
        vertx.deployVerticle(verticle, verticle.options(), res -> {
            if (res.succeeded()) {
                String dId = res.result();
                String vName = verticle.getClass().getName();
                storage.add(JsonResult.allocateTwo().put(DEPLOY_ID, dId).put(VERTICLE_NAME, vName));
            } else {
                log.error("scanning {} error happened.", verticle.getClass().getName());
            }
        });
    }
}
