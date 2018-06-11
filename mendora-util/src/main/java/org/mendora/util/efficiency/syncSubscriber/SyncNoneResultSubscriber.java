package org.mendora.util.efficiency.syncSubscriber;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.mendora.util.result.AsyncHandlerResult;
import rx.Subscriber;

/**
 * created by:xmf
 * date:2018/6/6
 * description:同步订阅者，专门用于执行同步操作，无返回接口
 */
public class SyncNoneResultSubscriber extends Subscriber<JsonObject> {
    private Handler<AsyncResult<JsonObject>> handler;

    public SyncNoneResultSubscriber(Handler<AsyncResult<JsonObject>> handler) {
        this.handler = handler;
    }

    @Override
    public void onCompleted() {
        AsyncHandlerResult.succ(handler);
    }

    @Override
    public void onError(Throwable e) {
        AsyncHandlerResult.fail(e, handler);
    }

    @Override
    public void onNext(JsonObject object) {

    }

    @Override
    public void onStart() {
        this.request(1);
    }
}
