package org.mendora.facade.data.postgre;

import org.mendora.facade.annotation.facade.ServiceFacade;
import org.mendora.facade.data.mongo.MongoAccesserVertxEBProxy;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
@ServiceFacade(proxy = MongoAccesserVertxEBProxy.class)
public interface PostgreAccesser {

    String EB_ADDRESS = "eb.data.postgre.accesser";

    @Fluent
    PostgreAccesser unRegister(Handler<AsyncResult<Void>> handler);

    @Fluent
    PostgreAccesser pause(Handler<AsyncResult<Void>> handler);

    @Fluent
    PostgreAccesser resume(Handler<AsyncResult<Void>> handler);

    @Fluent
    PostgreAccesser isRegistered(Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser query(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser queryWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser querySingle(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser querySingleWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser update(String sql, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser updateWithParams(JsonObject doc, Handler<AsyncResult<JsonObject>> handler);

    @Fluent
    PostgreAccesser execute(String sql, Handler<AsyncResult<JsonObject>> handler);
}
