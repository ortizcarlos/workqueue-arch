package co.carlosortiz.workqueue.workers.units.pipelines;

import co.carlosortiz.workqueue.core.job.JobExecution;
import co.carlosortiz.workqueue.workers.units.processors.JobProcessor;

public interface JobPipeline<T> {

    public String getName();

    public JobProcessor<T> getProcessor();

    public void processPipeline(JobExecution jobExecution,Object  result);

    public long getMaxExecutionMillis();

    public void handleTimeout(JobExecution jobExecution);
}
