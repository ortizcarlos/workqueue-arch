package co.carlosortiz.workqueue.core.queue2web.impl;

import co.carlosortiz.workqueue.core.events.JobAggregationCompleted;
import co.carlosortiz.workqueue.core.events.WebResponseCompleted;
import co.carlosortiz.workqueue.core.queue2web.KWQQueue2AsyncWebWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KWQQueue2AsyncWebWatcherImpl implements KWQQueue2AsyncWebWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(KWQQueue2AsyncWebWatcherImpl.class);


    private Map<String, DeferredResult<ResponseEntity<String>>> openWebChannels;

    public KWQQueue2AsyncWebWatcherImpl() {
        openWebChannels = new ConcurrentHashMap<>();
    }

    @Override
    public void registerAsyncWebWatch(String jobId, DeferredResult deferredResult) {
        openWebChannels.put(jobId, deferredResult);
    }

    @EventListener
    public void handleJobAggreationCompleted(WebResponseCompleted webResponseCompleted) {
        LOGGER.info("Processing event WebResponseCompleted for id {}",
                webResponseCompleted.getJobResponse().getJobId());

        DeferredResult<ResponseEntity<String>> deferredResult
                = openWebChannels.get(webResponseCompleted.getJobResponse().getJobId());

        if (deferredResult != null) {
            LOGGER.debug("Sending response to the webchannel linked to the job {} ",
                    webResponseCompleted.getJobResponse().getJobId());

            deferredResult.setResult(new ResponseEntity<String>(
                    webResponseCompleted.getWebContent(),
                    HttpStatus.OK));
            openWebChannels.remove(webResponseCompleted.getJobResponse().getJobId());
        }
    }
}
