package co.carlosortiz.workqueue.workers.units.job;

import co.carlosortiz.workqueue.workers.units.pipelines.HealthSupplyScheduleAppointmentPipeline;
import co.carlosortiz.workqueue.workers.units.pipelines.JobPipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@Component
public class JobExecutorInstanceBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutorInstanceBuilder.class);


    @Autowired
    private ApplicationContext context;

    public JobPipeline getJobExecutorInstance(String code) {
        return defineJobPipeline(code);
    }

    private JobPipeline defineJobPipeline(String code) {

        LOGGER.debug("Defining JobPipeline for JobCode " + code);
        Map<String,JobPipeline>  pipelineMap =
                context.getBeansOfType(JobPipeline.class);
        JobPipeline jobPipeline = null;

        for (JobPipeline pipeline : pipelineMap.values()) {
            if (pipeline.getName().equals(code)) {
                jobPipeline = pipeline;
            }
        }

        return jobPipeline;
    }

}
