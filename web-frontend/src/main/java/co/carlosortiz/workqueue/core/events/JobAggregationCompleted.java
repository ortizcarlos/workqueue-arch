package co.carlosortiz.workqueue.core.events;

import co.carlosortiz.workqueue.core.workqueue.JobResponse;

import java.util.List;

public class JobAggregationCompleted {
    private JobResponse jobResponse;


    public JobAggregationCompleted(JobResponse jobResponse) {
            this.jobResponse = jobResponse;
    }

    public JobResponse getJobResponse() {
        return jobResponse;
    }
}
