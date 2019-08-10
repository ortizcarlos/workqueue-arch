package co.carlosortiz.workqueue.aplicacion.events;

import co.carlosortiz.workqueue.core.workqueue.JobResponse;

public class HealthSupplyScheduleAppointmentReceived {
    private JobResponse jobResponse;

    public HealthSupplyScheduleAppointmentReceived(JobResponse jobResponse) {
        this.jobResponse = jobResponse;
    }

    public JobResponse getJobResponse() {
        return jobResponse;
    }
}
