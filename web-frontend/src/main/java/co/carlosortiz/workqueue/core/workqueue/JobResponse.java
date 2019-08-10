package co.carlosortiz.workqueue.core.workqueue;

import java.util.List;

public class JobResponse {
    private String jobCode;
    private String jobId;
    private String user;
    List<String> aggregatedResult;

    public JobResponse(String jobCode, String jobId, String user, List<String> aggregatedResult) {
        this.jobCode = jobCode;
        this.jobId = jobId;
        this.user = user;
        this.aggregatedResult = aggregatedResult;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getJobId() {
        return jobId;
    }

    public String getUser() {
        return user;
    }

    public List<String> getAggregatedResult() {
        return aggregatedResult;
    }
}
