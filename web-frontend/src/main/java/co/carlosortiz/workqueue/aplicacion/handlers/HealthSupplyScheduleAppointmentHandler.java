package co.carlosortiz.workqueue.aplicacion.handlers;

import co.carlosortiz.workqueue.aplicacion.events.HealthSupplyScheduleAppointmentReceived;
import co.carlosortiz.workqueue.core.events.WebResponseCompleted;
import co.carlosortiz.workqueue.core.workqueue.JobResultCoordinator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HealthSupplyScheduleAppointmentHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthSupplyScheduleAppointmentHandler.class);

    private ApplicationEventPublisher publisher;

    @Autowired
    public HealthSupplyScheduleAppointmentHandler(ApplicationEventPublisher publisher) {

        this.publisher = publisher;
    }

    @EventListener
    public void handleApplicationEvent(HealthSupplyScheduleAppointmentReceived healthSupplyScheduleAppointmentReceived) {
        LOGGER.info("Starting Handling of HealthSupplyScheduleAppointmentReceived");
        //se hacen las transformaciones necesarias y se genera la respuesta al cliente
        publisher.publishEvent(new WebResponseCompleted (
                    healthSupplyScheduleAppointmentReceived
                        .getJobResponse(),
                    healthSupplyScheduleAppointmentReceived
                        .getJobResponse()
                        .getAggregatedResult()
                        .toString())
        );

    }

}
