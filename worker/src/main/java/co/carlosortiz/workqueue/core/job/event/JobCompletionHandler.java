package co.carlosortiz.workqueue.core.job.event;

import co.carlosortiz.workqueue.core.job.JobExecution;
import co.carlosortiz.workqueue.workers.units.pipelines.JobPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class JobCompletionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionHandler.class);

    private ConcurrentHashMap<JobExecution, JobPipeline > jobsInExecutions;
    private boolean keepRunning;

    private ExecutorService executionService;

    public JobCompletionHandler() {
        keepRunning = true;
        jobsInExecutions = new ConcurrentHashMap<>();
        executionService = Executors.newSingleThreadExecutor();
        executionService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runWatchTask();
                } catch (InterruptedException ie) {

                }
            }
        });
    }

    public void submitJobCompletion(JobExecution jobExecution, JobPipeline pipeline) {
        jobsInExecutions.put(jobExecution,pipeline);
    }

    private void runWatchTask() throws InterruptedException {
        while(keepRunning) {
            for (Map.Entry<JobExecution, JobPipeline > entry : jobsInExecutions.entrySet()) {

                JobExecution jobExecution = entry.getKey();
                JobPipeline jobPipeline = entry.getValue();
                Future future = entry.getKey().getJobCompletion();

                if (future.isDone()) {
                    jobsInExecutions.remove(entry.getKey());
                    try {
                        jobPipeline.processPipeline(jobExecution,future.get());
                    } catch (ExecutionException ee) {
                        //TODO better exception handling
                        LOGGER.error(ee.getMessage());
                    }
                } else if (isTimedOut(entry.getKey(),jobPipeline.getMaxExecutionMillis())) {
                    //handle timeout
                    future.cancel(true);
                }
            }
            Thread.sleep(100);
        }
    }

    private boolean isTimedOut(JobExecution jobExecution,long maxMillis) {
        return (System.currentTimeMillis() - jobExecution.getStartmillis()> maxMillis );
    }

}
