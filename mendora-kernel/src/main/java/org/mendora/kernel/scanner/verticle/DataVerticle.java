package org.mendora.kernel.scanner.verticle;

import io.vertx.core.DeploymentOptions;
import lombok.extern.slf4j.Slf4j;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.scanner.service.provider.ServiceProviderScanner;

/**
 * created by:xmf
 * date:2018/3/12
 * description:
 */
@Slf4j
public class DataVerticle extends DefaultVerticle {
    @Override
    public DeploymentOptions options() {
        return super.options();
    }

    @Override
    public void start() throws Exception {
        // scanning service provider implementation
        ServiceProviderScanner scanner = new ServiceProviderScanner();
        scanner.scan(config.property(Const.PROVIDER_PACKAGE), injector.getInstance(ClassLoader.class), injector);
    }
}
