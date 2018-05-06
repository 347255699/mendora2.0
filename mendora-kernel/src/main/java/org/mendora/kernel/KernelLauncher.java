package org.mendora.kernel;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.log4j.PropertyConfigurator;
import org.mendora.kernel.binder.KernelBinder;
import org.mendora.kernel.cluster.Cluster;
import org.mendora.kernel.config.KernelConfig;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.properties.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by kam on 2018/3/18.
 */
public class KernelLauncher {
    public static void launch(KernelConfig kernelConfig) {
        URL rootUrl = kernelConfig.getRootUrl();
        // loading app root path.
        String rootPath = rootUrl.getPath().substring(0, rootUrl.getPath().lastIndexOf("/"));
        System.setProperty(Const.ROOT_PATH, rootPath);

        KernelBinder binder = new KernelBinder();
        binder.setClassLoader(kernelConfig.getClassLoader());
        binder.setKernelConfig(kernelConfig);
        // building system configure.
        Injector injector = Guice.createInjector(binder);
        SysConfig config = injector.getInstance(SysConfig.class);

        // initialization logger
        System.setProperty("vertx.logger-delegate-factory-class-name", config.property(Const.LOGGER_FACTORY_CLASS));
        PropertyConfigurator.configure(rootPath + config.property(Const.LOGGER_CONFIG_PATH));
        Logger logger = LoggerFactory.getLogger(KernelLauncher.class);
        config.asJson().forEach(e -> {
            logger.info("System options: {}", e.getKey() + "|" + e.getValue());
        });
        // launching cluster.
        Cluster.launch(injector);
    }

}
