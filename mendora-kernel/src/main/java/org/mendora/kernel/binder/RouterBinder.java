package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.rxjava.ext.web.Router;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by kam on 2018/3/18.
 */
@RequiredArgsConstructor
public class RouterBinder extends AbstractModule {
    @NonNull
    private Router router;

    @Provides
    @Singleton
    public Router provideRouter() {
        return this.router;
    }

}
