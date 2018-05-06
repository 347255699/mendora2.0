package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.Setter;
import org.mendora.kernel.config.KernelConfig;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.properties.PropertiesLoader;

/**
 * Created by kam on 2018/5/5.
 */
public class KernelBinder extends AbstractModule {
    @Setter
    private ClassLoader classLoader;

    @Setter
    private KernelConfig kernelConfig;

    @Override
    protected void configure() {
        String rootPath = System.getProperty(Const.ROOT_PATH);
        bind(PropertiesLoader.class)
                .toInstance(new PropertiesLoader(rootPath + "/config/config.properties"));
    }

    @Provides
    public ClassLoader provideClassLoader() {
        return this.classLoader;
    }

    @Provides
    public KernelConfig provideKernelConfig() {
        return this.kernelConfig;
    }
}
