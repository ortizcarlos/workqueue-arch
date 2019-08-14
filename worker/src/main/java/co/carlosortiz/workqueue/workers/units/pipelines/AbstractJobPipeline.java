package co.carlosortiz.workqueue.workers.units.pipelines;

import co.carlosortiz.workqueue.core.job.JobExecution;
import co.carlosortiz.workqueue.core.job.JobRequest;
import co.carlosortiz.workqueue.core.job.event.JobCompletionEvent;
import co.carlosortiz.workqueue.core.job.event.JobCompletionPublisher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public abstract class AbstractJobPipeline<T> implements  JobPipeline<T> {
    @Autowired
    private JobCompletionPublisher jobExecutionPublisher;

    @Override
    public void processPipeline(JobExecution jobExecution, Object result) {
        JobRequest jobRequest = jobExecution.getJobRequest();
        HashMap<String,String> resultMap = runPipeline(result);
        JobCompletionEvent jobCompletionEvent = new JobCompletionEvent( jobRequest.getJobId(),
                jobRequest.getUser(),jobRequest.getJobCode(),resultMap);
        jobExecutionPublisher.publish(jobCompletionEvent);
    }

    public abstract HashMap<String,String> runPipeline(Object result);

    @Override
    public void handleTimeout(JobExecution jobExecution) {
        String timeoutMsg = onTimeout(jobExecution);
        JobRequest jobRequest = jobExecution.getJobRequest();
        JobCompletionEvent jobCompletionEvent =  JobCompletionEvent.newFailureCompletionEvent ( jobRequest.getJobId(),
                jobRequest.getUser(),jobRequest.getJobCode(), timeoutMsg );
        jobExecutionPublisher.publish(jobCompletionEvent);
    }

    public abstract String onTimeout(JobExecution jobExecution);

}
