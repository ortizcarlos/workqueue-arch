package co.carlosortiz.workqueue.core.workqueue;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JobRequestDefinition {
    private int jobsQueued;
    private AtomicInteger jobsExecuted;
    private Queue<String> resultQueue;


    public JobRequestDefinition(int jobsQueued) {
        this.jobsQueued = jobsQueued;
        jobsExecuted = new AtomicInteger(0);
        resultQueue = new ConcurrentLinkedQueue<>();
    }

    public int incrementAndGetExecutions() {
        return jobsExecuted.incrementAndGet();
    }

    public int getJobsQueued() {
        return jobsQueued;
    }

    public void addJobResult(String result) {
        resultQueue.add(result);
    }

    public List<String> getAggregatedResult() {
        return resultQueue.stream().collect(Collectors.toList());
    }
}
