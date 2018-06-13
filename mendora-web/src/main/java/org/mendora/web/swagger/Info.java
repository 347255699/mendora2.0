package org.mendora.web.swagger;

import io.vertx.core.json.JsonObject;
import org.mendora.util.result.JsonResult;

/**
 * Created by kam on 2018/5/14.
 */
public class Info {
    private JsonObject data;
    private String title;
    private String description;
    private String termsOfService;
    private JsonObject contact;
    private JsonObject license;
    private String version;

    public Info() {
        this.data = JsonResult.allocate(6);
    }

    public Info title(String title) {
        this.title = title;
        return this;
    }

    public Info description(String description) {
        this.description = description;
        return this;
    }

    public Info termsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
        return this;
    }

    public Info contact(JsonObject contact) {
        this.contact = contact;
        return this;
    }

    public Info license(JsonObject license) {
        this.license = license;
        return this;
    }

    public Info version(String version) {
        this.version = version;
        return this;
    }

    public JsonObject toJson() {
        return data;
    }
}
