package co.carlosortiz.workqueue.workers.units.pipelines;

import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.JobCompletionEvent;
import co.carlosortiz.workqueue.interfaces.reportes.JobExecutionPublisher;
import co.carlosortiz.workqueue.interfaces.reportes.ReportExecutionPublisher;
import co.carlosortiz.workqueue.workers.units.job.JobExecution;
import co.carlosortiz.workqueue.workers.units.job.JobRequest;
import co.carlosortiz.workqueue.workers.units.processors.HealSupplyScheduleAppointmentJobProcessor;
import co.carlosortiz.workqueue.workers.units.processors.JobProcessor;
import co.carlosortiz.workqueue.workers.units.reportes.impl.ReportProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Component
public class HealthSupplyScheduleAppointmentPipeline implements JobPipeline<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthSupplyScheduleAppointmentPipeline.class);


    @Autowired
    private JobExecutionPublisher jobExecutionPublisher;

    @Override
    public JobProcessor getProcessor() {
        return new HealSupplyScheduleAppointmentJobProcessor();
    }

    public String getName() {
        return "appointment-schedule";
    }

    @Override
    public void processPipeline(JobExecution jobExecution, Object  result) {
        String jobResult = (String) result;
        JobRequest jobRequest = jobExecution.getJobRequest();

        HashMap<String,String> resultMap = new HashMap<>();
        resultMap.put("result",jobResult);

        JobCompletionEvent jobCompletionEvent = new JobCompletionEvent( jobRequest.getJobId(),
                jobRequest.getUser(),jobRequest.getJobCode(),resultMap);
        jobExecutionPublisher.publish(jobCompletionEvent);
    }

    @Override
    public long getMaxExecutionMillis() {
        return 15000;
    }
}
