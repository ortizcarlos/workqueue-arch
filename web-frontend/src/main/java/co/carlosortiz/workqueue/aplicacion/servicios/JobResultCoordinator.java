/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.aplicacion.servicios;

import co.carlosortiz.workqueue.core.events.JobAggregationCompleted;
import co.carlosortiz.workqueue.core.workqueue.JobRequestDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Carlos
 */
@Component
public class JobResultCoordinator {


    private static final Logger LOGGER = LoggerFactory.getLogger(JobResultCoordinator.class);

    private Map<String, DeferredResult<ResponseEntity<String>>> openWebChannels;
    private Map<String, JobRequestDefinition> jobsInProgress;

    private  ApplicationEventPublisher publisher;

    @Autowired
    public JobResultCoordinator(ApplicationEventPublisher publisher) {
        openWebChannels = new ConcurrentHashMap<>();
        jobsInProgress = new ConcurrentHashMap<>();
        this.publisher = publisher;
    }

    public void registerJobCreation(String id, JobRequestDefinition jobRequestDefinition,
                                    DeferredResult<ResponseEntity<String>> result) {
        openWebChannels.put(id, result);
        jobsInProgress.put(id,jobRequestDefinition);
    }

    public void registerJobCreation(String id, JobRequestDefinition jobRequestDefinition) {
        jobsInProgress.put(id,jobRequestDefinition);
    }


    public void processJobCompleted(String jobId, String jobResponse) {
        LOGGER.info("processing result for JobID {}" , jobId);
        JobRequestDefinition jobRequestDefinition = jobsInProgress.get(jobId);

        if (jobRequestDefinition!=null) {
            int executions = jobRequestDefinition.incrementAndGetExecutions();
            jobRequestDefinition.addJobResult(jobResponse);
            LOGGER.info("executions completed " + executions );

            if (executions==jobRequestDefinition.getJobsQueued()) {
                LOGGER.info("Aggregation completed sending local event -> JobAggregationCompleted ");
                publisher.publishEvent(new JobAggregationCompleted(jobId,jobRequestDefinition.getAggregatedResult()));
            }
        } else {
            LOGGER.info("No JobRequest found for id " + jobId);
        }

    }



}
