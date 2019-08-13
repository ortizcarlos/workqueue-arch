package co.carlosortiz.workqueue.core.scheduler.impl;

import co.carlosortiz.workqueue.core.job.event.JobCompletionHandler;
import co.carlosortiz.workqueue.core.job.JobExecution;
import co.carlosortiz.workqueue.core.job.JobRequest;
import co.carlosortiz.workqueue.core.scheduler.WorkScheduler;
import co.carlosortiz.workqueue.workers.units.pipelines.JobPipeline;
import co.carlosortiz.workqueue.workers.units.processors.JobProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class DefaultWorkSchedulerImpl implements WorkScheduler {
    private ExecutorService jobExecutorService;


    private JobCompletionHandler jobCompletionHandler;
    private static final int MAXTHREADS = 5;
    private int maxthreads = MAXTHREADS;

    @Autowired
    public DefaultWorkSchedulerImpl(JobCompletionHandler jobCompletionHandler){
        jobExecutorService = Executors.newFixedThreadPool(maxthreads);
        this.jobCompletionHandler = jobCompletionHandler;
    }

    @Override
    public void submitJob(JobPipeline jobPipeline, JobProcessor jobProcessor, JobRequest jobRequest) {
        Future<Callable> completion =  jobExecutorService.submit(jobProcessor);
        jobCompletionHandler.submitJobCompletion(new JobExecution(completion,
                jobRequest,
                System.currentTimeMillis()),
                jobPipeline);

    }
}
