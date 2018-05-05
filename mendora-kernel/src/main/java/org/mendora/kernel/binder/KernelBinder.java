package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mendora.kernel.properties.Const;
import org.mendora.kernel.properties.PropertiesLoader;

/**
 * Created by kam on 2018/5/5.
 */
@RequiredArgsConstructor
public class KernelBinder extends AbstractModule {
    @NonNull
    private ClassLoader cl;

    @Override
    protected void configure() {
        String rootPath = System.getProperty(Const.ROOT_PATH);
        bind(PropertiesLoader.class)
                .toInstance(new PropertiesLoader(rootPath + "/config/config.properties"));
    }

    @Provides
    public ClassLoader provideClassLoader() {
        return this.cl;
    }
}
