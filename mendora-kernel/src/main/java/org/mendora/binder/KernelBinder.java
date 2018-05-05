package org.mendora.binder;

import com.google.inject.AbstractModule;
import org.mendora.properties.Const;
import org.mendora.properties.PropertiesLoader;

/**
 * Created by kam on 2018/5/5.
 */
public class KernelBinder extends AbstractModule {

    @Override
    protected void configure() {
        String rootPath = System.getProperty(Const.ROOT_PATH);
        bind(PropertiesLoader.class)
                .toInstance(new PropertiesLoader(rootPath + "/config/config.properties"));
    }
}
