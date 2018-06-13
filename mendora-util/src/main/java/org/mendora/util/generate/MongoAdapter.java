package org.mendora.util.generate;

import org.mendora.util.constant.MongoReferences;
import org.mendora.util.constant.MongoSymbol;
import org.mendora.util.result.JsonResult;
import io.vertx.core.json.JsonObject;
import rx.Single;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * created by:xmf
 * date:2018/4/3
 * description:
 */
public class MongoAdapter {

    // 时间区间，左区间
    public final static String PAGE_MODEL_START_TIME = "startTime";
    // 时间区间，右区间
    public final static String PAGE_MODEL_END_TIME = "endTime";
    // 近几天
    public final static String PAGE_MODEL_RECENT = "recent";

    /**
     * 转换查询集中的时间区间，若有的话，字段名中需要与上述常量一致
     *
     * @param query{    startTime:时间区间, String 左区间 格式(2018-01-11 00:00)
     *                  endTime:时间区间, String 右区间 格式(2018-01-11 00:00)
     *                  }
     * @param timeField 时间字段(数据库中的时间字段名)
     * @return
     */
    public static Single<JsonObject> rxDateRange(JsonObject query, String timeField) {
        boolean hasTimeRange = query.containsKey(PAGE_MODEL_START_TIME) || query.containsKey(PAGE_MODEL_END_TIME);
        boolean hasRecent = query.containsKey(PAGE_MODEL_RECENT);
        JsonObject dateRange;
        if ((hasTimeRange && hasRecent) || hasTimeRange) {
            // 以自定义时间区间为准
            dateRange = JsonResult.allocateTwo();
            if (query.containsKey(PAGE_MODEL_START_TIME) && query.containsKey(PAGE_MODEL_END_TIME))
                dateRange.put(MongoSymbol.$gte, (String) query.remove(PAGE_MODEL_START_TIME)).put(MongoSymbol.$lte, (String) query.remove(PAGE_MODEL_END_TIME));
            else if (query.containsKey(PAGE_MODEL_START_TIME))
                dateRange.put(MongoSymbol.$gte, (String) query.remove(PAGE_MODEL_START_TIME));
            else dateRange.put(MongoSymbol.$lte, (String) query.remove(PAGE_MODEL_END_TIME));
            query.put(timeField, dateRange);
        }
        if (!hasTimeRange && hasRecent) {
            // 以确定的时间区间为准(近几天)
            String localDate;
            dateRange = JsonResult.allocateOne();
            int recent = (Integer) query.remove(PAGE_MODEL_RECENT);
            if (recent > 1) {
                localDate = LocalDate.now().minusDays(--recent)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).concat(" 00:00");
            } else {
                localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).concat(" 00:00");
            }
            dateRange.put(MongoSymbol.$gte, localDate);
            query.put(timeField, dateRange);
        }
        return Single.just(query);
    }

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
        return JsonResult.allocateOne()
                .put(MongoReferences.COLLECTION.val(), colName)
                .put(MongoReferences.DOCUMENT.val(), doc);
    }
}
