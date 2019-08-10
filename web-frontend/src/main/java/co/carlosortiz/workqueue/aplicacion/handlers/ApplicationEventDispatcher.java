package co.carlosortiz.workqueue.aplicacion.handlers;

import co.carlosortiz.workqueue.aplicacion.events.HealthSupplyScheduleAppointmentReceived;
import co.carlosortiz.workqueue.core.events.JobAggregationCompleted;
import co.carlosortiz.workqueue.core.workqueue.JobResultCoordinator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApplicationEventDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobResultCoordinator.class);

    private ApplicationEventPublisher publisher;

    @Autowired
    public ApplicationEventDispatcher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void handleJobAggreationCompleted(JobAggregationCompleted jobAggregationCompleted) {

        LOGGER.info("Processing event JobAggregationCompleted for jobCode {} jobId {}",
                jobAggregationCompleted.getJobResponse().getJobCode(),
                jobAggregationCompleted.getJobResponse().getJobId());

        if ( "appointment-schedule".equals(
                        jobAggregationCompleted.getJobResponse().getJobCode()) ) {

            LOGGER.info("dispatching appointment-schedule");

            publisher.publishEvent(new HealthSupplyScheduleAppointmentReceived(
                                                        jobAggregationCompleted.getJobResponse()));

        }
    }
}
