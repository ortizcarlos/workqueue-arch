package co.carlosortiz.workqueue.core.queue2web;

import org.springframework.web.context.request.async.DeferredResult;

public interface KWQQueue2AsyncWebWatcher {
    void registerAsyncWebWatch(String jobId, DeferredResult deferredResult);
}
