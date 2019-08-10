package co.carlosortiz.workqueue.workers.units.job;

import java.util.Map;

public class JobRequest {
    private String jobId;
    private String user;
    private String jobCode;
    private Map<String,String> jobParams;

    public JobRequest(String jobId, String user, String jobCode, Map<String, String> jobParams) {
        this.jobId = jobId;
        this.user = user;
        this.jobCode = jobCode;
        this.jobParams = jobParams;
     }

    public Map<String, String> getJobParams() {
        return jobParams;
    }

    public String getJobId() {
        return jobId;
    }

    public String getUser() {
        return user;
    }

    public String getJobCode() {
        return jobCode;
    }
}
