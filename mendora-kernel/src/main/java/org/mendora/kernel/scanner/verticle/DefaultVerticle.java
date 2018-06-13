package org.mendora.kernel.scanner.verticle;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import lombok.extern.slf4j.Slf4j;
import org.mendora.kernel.properties.SysConfig;

/**
 * Created by kam on 2018/2/4.
 */
@Slf4j
public abstract class DefaultVerticle extends AbstractVerticle {
    @Inject
    protected SysConfig config;

    protected Injector injector;

    public DeploymentOptions options() {
        return new DeploymentOptions();
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
