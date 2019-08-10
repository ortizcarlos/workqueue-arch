package co.carlosortiz.workqueue.core.workqueue.impl;

import co.carlosortiz.workqueue.core.workqueue.JobResultCoordinator;
import co.carlosortiz.workqueue.core.workqueue.KWQPublisher;
import co.carlosortiz.workqueue.core.workqueue.JobRequestPublisher;
import co.carlosortiz.workqueue.core.workqueue.JobRequest;
import co.carlosortiz.workqueue.core.workqueue.JobRequestDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class KWQPublisherImpl implements KWQPublisher {

    private JobResultCoordinator jobResultCoordinator;
    private JobRequestPublisher jobRequestPublisher;

    @Autowired
    public KWQPublisherImpl(JobRequestPublisher jobRequestPublisher,
                               JobResultCoordinator jobResultCoordinator) {
        this.jobRequestPublisher = jobRequestPublisher;
        this.jobResultCoordinator = jobResultCoordinator;
    }

    @Override
    public String submitJob(JobRequest jobRequest) {
        JobRequestDefinition jobRequestDefinition = new JobRequestDefinition(1,jobRequest.getJobCode(),jobRequest.getUser());
        jobResultCoordinator.registerJobCreation(jobRequest.getJobId(), jobRequestDefinition);
        return jobRequest.getJobId();
    }

    @Override
    public String submitJobAggregation(List<JobRequest> jobRequests) {
        if (null==jobRequests) {
            throw new NullPointerException("JobRequest list is null");
        }

        if (jobRequests.isEmpty()) {
            throw new IllegalArgumentException("JobRequests is empty");
        }

        String aggregationId = UUID.randomUUID().toString();
        int numJobs = jobRequests.size();
        JobRequestDefinition jobRequestDefinition = new JobRequestDefinition(numJobs,
                jobRequests.get(0).getJobCode(),
                jobRequests.get(0).getUser());

        jobRequests.stream().forEach(jobRequest -> jobRequest.setJobAggregationId(aggregationId));
        jobResultCoordinator.registerJobCreation(aggregationId, jobRequestDefinition);

        for (JobRequest jobRequest : jobRequests) {
            jobRequestPublisher.publish(jobRequest);
        }

        return aggregationId;
    }
}
