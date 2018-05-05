package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.eventbus.EventBus;
import io.vertx.rxjava.core.shareddata.SharedData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by kam on 2018/5/6.
 */
@RequiredArgsConstructor
public class VertxBinder extends AbstractModule {
    @NonNull
    private Vertx vertx;

    @Provides
    @Singleton
    public Vertx provideVertx() {
        return this.vertx;
    }

    @Provides
    @Singleton
    public io.vertx.core.Vertx provideVertxDelegate() {
        return this.vertx.getDelegate();
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return this.vertx.eventBus();
    }

    @Provides
    @Singleton
    public SharedData provideSharedData() {
        return this.vertx.sharedData();
    }
}
