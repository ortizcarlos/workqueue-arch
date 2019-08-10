package co.carlosortiz.workqueue.core.events;

import java.util.List;

public class JobAggregationCompleted {
    private String aggregationId;
    List<String> jobResults;


    public JobAggregationCompleted(String aggregationId, List<String> jobResults) {
        this.aggregationId = aggregationId;
        this.jobResults = jobResults;
    }

    public List<String> getJobResults() {
        return jobResults;
    }

    public String getAggregationId() {
        return aggregationId;
    }
}
