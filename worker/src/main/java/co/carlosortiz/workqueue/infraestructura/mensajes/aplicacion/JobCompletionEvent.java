package co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion;

import java.util.Map;

public class JobCompletionEvent {

    private String jobId;
    private String user;
    private String jobCode;
    private Map<String,String> responseMap;


    public JobCompletionEvent(String jobId, String user, String jobCode, Map<String, String> responseMap) {
        this.jobId = jobId;
        this.user = user;
        this.jobCode = jobCode;
        this.responseMap = responseMap;
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
