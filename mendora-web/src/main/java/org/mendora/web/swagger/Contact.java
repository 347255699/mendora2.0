package org.mendora.web.swagger;

import io.vertx.core.json.JsonObject;

/**
 * Created by kam on 2018/5/14.
 */
public class Contact {
    public JsonObject data;
    public String name;
    public String url;
    public String email;

    public Contact name(String name) {
        this.name = name;
        return this;
    }

    public Contact url(String url) {
        this.url = url;
        return this;
    }

    public Contact email(String email) {
        this.email = email;
        return this;
    }

    public JsonObject toJson() {
        return this.data;
    }

}
