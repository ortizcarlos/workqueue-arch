package co.carlosortiz.workqueue.core.workqueue;

import java.util.List;

public interface KWQPublisher {

    String submitJob(JobRequest jobRequest);

    String submitJobAggregation(List<JobRequest> jobRequests);

}
