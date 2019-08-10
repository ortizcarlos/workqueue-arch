package co.carlosortiz.workqueue.core.workqueue;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JobRequestDefinition {
    private int jobsQueued;
    private String jobCode;
    private String jobUser;
    private AtomicInteger jobsExecuted;
    private Queue<String> resultQueue;


    public JobRequestDefinition(int jobsQueued,String jobCode,String jobUser) {
        this.jobsQueued = jobsQueued;
        this.jobCode = jobCode;
        jobsExecuted = new AtomicInteger(0);
        resultQueue = new ConcurrentLinkedQueue<>();
        this.jobUser = jobUser;
    }

    public int incrementAndGetExecutions() {
        return jobsExecuted.incrementAndGet();
    }

    public int getJobsQueued() {
        return jobsQueued;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getJobUser() {
        return jobUser;
    }

    public void addJobResult(String result) {
        resultQueue.add(result);
    }

    public List<String> getAggregatedResult() {
        return resultQueue.stream().collect(Collectors.toList());
    }
}
