package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.rxjava.ext.web.Router;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mendora.kernel.scanner.route.WebResult;

/**
 * Created by kam on 2018/3/18.
 */
@RequiredArgsConstructor
public class WebBinder extends AbstractModule {
    @Setter
    private Router router;
    @Setter
    private WebResult webResult;

    @Provides
    @Singleton
    public Router provideRouter() {
        return this.router;
    }

    @Provides
    @Singleton
    public WebResult provideWebResult() {
        return this.webResult;
    }

}
