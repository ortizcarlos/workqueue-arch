package co.carlosortiz.workqueue.core.scheduler;

import co.carlosortiz.workqueue.core.job.JobRequest;
import co.carlosortiz.workqueue.workers.units.pipelines.JobPipeline;
import co.carlosortiz.workqueue.workers.units.processors.JobProcessor;

public interface WorkScheduler {
    void submitJob(JobPipeline jobPipeline, JobProcessor jobProcessor,JobRequest jobRequest);
}
