package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.templ.TemplateEngine;
import io.vertx.rxjava.ext.web.templ.ThymeleafTemplateEngine;
import lombok.Setter;
import org.mendora.kernel.scanner.route.WebResult;

/**
 * Created by kam on 2018/3/18.
 */
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

    @Provides
    @Singleton
    public TemplateEngine provideThymeleafTemplateEngine() {
        return ThymeleafTemplateEngine.create();
    }

}
