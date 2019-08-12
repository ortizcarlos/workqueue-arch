/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.workqueue.web;

import co.carlosortiz.workqueue.core.queue2web.KWQQueue2AsyncWebWatcher;
import co.carlosortiz.workqueue.core.workqueue.JobRequest;
import co.carlosortiz.workqueue.core.workqueue.KWQPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos
 */
@RestController
//@RequestMapping("/job")
public class WorkQueueController {

    private KWQPublisher kwqPublisher;
    private KWQQueue2AsyncWebWatcher kwqQueue2AsyncWebWatcher;

    @Autowired
    public WorkQueueController(KWQPublisher kwqPublisher,
                               KWQQueue2AsyncWebWatcher kwqQueue2AsyncWebWatcher) {
        this.kwqPublisher = kwqPublisher;
        this.kwqQueue2AsyncWebWatcher = kwqQueue2AsyncWebWatcher;
    }

    @RequestMapping( method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<String>> create() {
        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult();
        JobRequest jobRequest =
                JobRequest
                        .builder()
                        .jobCode("appointment-schedule")
                        .user("cortiz")
                        .addJobParam("ips","camino")
                        .create();
        JobRequest jobRequest2 =
                JobRequest
                        .builder()
                        .jobCode("appointment-schedule")
                        .user("cortiz")
                        .addJobParam("ips","ips2")
                        .create();
        JobRequest jobRequest3 =
                JobRequest
                        .builder()
                        .jobCode("appointment-schedule")
                        .user("cortiz")
                        .addJobParam("ips","ips3")
                        .create();
        List<JobRequest> jobRequests = new ArrayList<>();
        jobRequests.add(jobRequest);
        jobRequests.add(jobRequest2);
        jobRequests.add(jobRequest3);
        String aggregationId = kwqPublisher.submitJobAggregation(jobRequests);
        kwqQueue2AsyncWebWatcher.registerAsyncWebWatch(aggregationId,deferredResult);
        return deferredResult;
    }

}