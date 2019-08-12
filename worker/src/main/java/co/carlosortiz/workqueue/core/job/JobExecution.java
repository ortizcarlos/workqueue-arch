package co.carlosortiz.workqueue.core.job;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class JobExecution {
    private Future<Callable> jobCompletion;
    private JobRequest jobRequest;
    private long startmillis;
    private long maxExecutionMillis;


    public JobExecution(Future<Callable> jobCompletion, JobRequest jobRequest, long startmillis) {
        this.jobCompletion = jobCompletion;
        this.jobRequest = jobRequest;
        this.startmillis = startmillis;
    }

    @Override
    public int hashCode() {
        return jobCompletion.hashCode() + super.hashCode();
    }

    public Future<Callable> getJobCompletion() {
        return jobCompletion;
    }

    public long getStartmillis() {
        return startmillis;
    }

    public JobRequest getJobRequest() {
        return jobRequest;
    }
}
