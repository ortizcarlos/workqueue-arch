package co.carlosortiz.workqueue.core.job.event;

import java.util.HashMap;
import java.util.Map;

public class JobCompletionEvent {

    private String jobId;
    private String user;
    private String jobCode;
    private String status;
    private String statusMsg;
    private Map<String,String> responseMap;


    public JobCompletionEvent(String jobId, String user, String jobCode, Map<String, String> responseMap) {
        this.jobId = jobId;
        this.user = user;
        this.jobCode = jobCode;
        this.responseMap = responseMap;
        this.status = "Success";
    }

    public static JobCompletionEvent  newFailureCompletionEvent(String jobId, String user, String jobCode,String statusMsg) {
        JobCompletionEvent jobCompletionEvent = new JobCompletionEvent(jobId,user,jobCode,new HashMap<String,String>());
        jobCompletionEvent.status = "Error";
        return jobCompletionEvent;
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

    public Map<String, String> getResponseMap() {
        return responseMap;
    }
}
