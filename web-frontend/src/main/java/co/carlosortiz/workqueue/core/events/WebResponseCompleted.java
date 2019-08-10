package co.carlosortiz.workqueue.core.events;

import co.carlosortiz.workqueue.core.workqueue.JobResponse;

public class WebResponseCompleted {
    private JobResponse jobResponse;
    private String webContent;

    public WebResponseCompleted(JobResponse jobResponse, String webContent) {
        this.jobResponse = jobResponse;
        this.webContent = webContent;
    }

    public JobResponse getJobResponse() {
        return jobResponse;
    }

    public String getWebContent() {
        return webContent;
    }
}
