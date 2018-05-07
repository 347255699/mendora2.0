package org.mendora.util.generate;

import org.mendora.util.constant.MongoReferences;
import org.mendora.util.result.JsonResult;
import io.vertx.core.json.JsonObject;

/**
 * created by:xmf
 * date:2018/4/3
 * description:
 */
public class MongoAdapter {
    public static JsonObject findOne(String colName, JsonObject query, JsonObject fields) {
        return JsonResult.allocate(3)
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.QUERY.val(), query)
                .put(MongoReferences.FIELDS.val(), fields);
    }

    public static JsonObject find(String colName, JsonObject query, JsonObject findOptions) {
        return JsonResult.allocate(3)
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.QUERY.val(), query)
                .put(MongoReferences.FIELDS.val(), findOptions);
    }

    public static JsonObject find(String colName, JsonObject query) {
        return JsonResult.allocate(3)
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.QUERY.val(), query);
    }

    public static JsonObject save(String colName, JsonObject doc) {
        return JsonResult.two()
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.DOCUMENT.val(), doc);
    }
}
