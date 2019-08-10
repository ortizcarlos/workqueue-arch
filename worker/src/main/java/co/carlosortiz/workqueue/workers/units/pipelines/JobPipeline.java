package co.carlosortiz.workqueue.workers.units.pipelines;

import co.carlosortiz.workqueue.workers.units.job.JobExecution;
import co.carlosortiz.workqueue.workers.units.processors.JobProcessor;

import java.util.concurrent.Callable;

public interface JobPipeline<T> {

    public String getName();

    public JobProcessor<T> getProcessor();

    public void processPipeline(JobExecution jobExecution,Object  result);

    public long getMaxExecutionMillis();
}
