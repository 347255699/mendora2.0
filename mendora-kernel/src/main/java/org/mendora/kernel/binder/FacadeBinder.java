package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import io.vertx.core.Vertx;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by kam on 2018/4/1.
 */
@Slf4j
public class FacadeBinder extends AbstractModule {
    @Setter
    private List<Class> proxys;
    @Setter
    private List<Class> facades;
    @Setter
    private Vertx vertx;

    @Override
    protected void configure() {
        if (facades != null && vertx != null && proxys != null) {
            for (int i = 0; i < facades.size(); i++) {
                try {
                    Constructor cons = proxys.get(i).getConstructor(Vertx.class);
                    bind(facades.get(i)).toInstance(cons.newInstance((Object) vertx));
                } catch (Exception e) {
                    log.error("facade err[{}]", e.getCause().getMessage());
                }
            }
        }
    }
}
