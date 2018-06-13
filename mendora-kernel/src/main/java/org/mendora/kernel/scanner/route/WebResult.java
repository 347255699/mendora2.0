package org.mendora.kernel.scanner.route;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.mendora.util.constant.SysConst;
import org.mendora.util.result.JsonResult;

/**
 * created by:xmf
 * date:2018/3/13
 * description:response result set.
 */
public class WebResult {

    /**
     * only success status code.
     * example:{
     * "retCode":0
     * }
     */
    public void succ(RoutingContext rc) {
        common(JsonResult.succ().encode(), rc);
    }

    /**
     * return sucess status code and payload.
     * example:{
     * "retCode":0,
     * "rear":[payload]
     * }
     */
    public void succ(JsonObject payload, RoutingContext rc) {
        common(JsonResult.succ(payload).encode(), rc);
    }

    /**
     * return sucess status code and payload of json array format.
     * example:{
     * "retCode":0,
     * "rear":{
     * "size":[list size],
     * "rows":[rows]
     * }
     * }
     */
    public void succ(JsonArray rows, RoutingContext rc) {
        JsonObject payload = JsonResult.allocateTwo()
                .put(SysConst.SYS_ROWS, rows)
                .put(SysConst.SYS_SIZE, rows.size());
        common(JsonResult.succ(payload).encode(), rc);
    }

    /**
     * only failure status code.
     * example:{
     * "retCode":-1
     * }
     */
    public void fail(RoutingContext rc) {
        common(JsonResult.fail().encode(), rc);
    }

    /**
     * return failure status code and error msg.
     * example:{
     * "retCode":-1,
     * "errMsg":[err.getMessage()]
     * }
     */
    public void fail(Throwable err, RoutingContext rc) {
        common(JsonResult.fail(err).encode(), rc);
    }


    /**
     * return failure status code and error msg.
     * example:{
     * "retCode":-1,
     * "errMsg":[errMsg]
     * }
     */
    public void fail(String errMsg, RoutingContext rc){
        fail(new RuntimeException(errMsg), rc);
    }

    /**
     * only half success status code.
     * example:{
     * "retCode":1
     * }
     */
    public void halfSucc(RoutingContext rc) {
        common(JsonResult.halfSucc().encode(), rc);
    }

    /**
     * return half success status code and payload.
     * example:{
     * "retCode":1,
     * "rear":[payload]
     * }
     */
    public void halfSucc(JsonObject payload, RoutingContext rc) {
        common(JsonResult.halfSucc(payload).encode(), rc);
    }

    /**
     * return half success status code and payload of json array format.
     * example:{
     * "retCode":1,
     * "rear":{
     * "size":[list size],
     * "rows":[rows]
     * }
     * }
     */
    public void halfSucc(JsonArray rows, RoutingContext rc) {
        JsonObject payload = JsonResult.allocateTwo()
                .put(SysConst.SYS_ROWS, rows)
                .put(SysConst.SYS_SIZE, rows.size());
        common(JsonResult.halfSucc(payload).encode(), rc);
    }

    /**
     * customize your response payload event status code.
     *
     * @param payload
     * @param rc
     */
    public void consume(JsonObject payload, RoutingContext rc) {
        common(payload.encode(), rc);
    }

    /**
     * customize your response payload and default status code
     *
     * @param payload
     * @param rc
     */
    public void consume(JsonObject payload, int defaultRetCode, RoutingContext rc) {
        if (!payload.containsKey(SysConst.SYS_RET_CODE))
            payload.put(SysConst.SYS_RET_CODE, defaultRetCode);
        common(payload.encode(), rc);
    }

    /**
     * customize your response payload and default status code
     *
     * @param result
     */
    public void consume(JsonObject header, String result, RoutingContext rc) {
        HttpServerResponse response = rc.response();
        header.fieldNames().forEach(k -> response.putHeader(k, header.getString(k)));
        response.end(result);
    }

    /**
     * final response method.
     *
     * @param payload
     * @param rc
     */
    private void common(String payload, RoutingContext rc) {
        rc.response().end(payload);
    }

}
