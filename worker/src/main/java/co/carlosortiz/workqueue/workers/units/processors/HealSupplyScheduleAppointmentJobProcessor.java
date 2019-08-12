package co.carlosortiz.workqueue.workers.units.processors;

import co.carlosortiz.workqueue.core.job.JobExecutorInstanceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HealSupplyScheduleAppointmentJobProcessor implements JobProcessor<String>{

    private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutorInstanceBuilder.class);

    private Map<String,String> params;

    @Override
    public void setParams(Map<String, String> params) {

        this.params = params;
    }


    @Override
    public String call() {

        String ips = params.get("ips");
        String requestDate = params.get("requested-appointment-date");

        LOGGER.debug("IPS: " + ips);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
        }

        String response = "{\"appointment\": {\n" +
                "  \"jobid\": \" " + System.currentTimeMillis() + ",\n" +
                "  \"ips\": \" " + ips + "\",\n" +
                "  \"details\": {\n" +
                "    \"schedule\": [\n" +
                "      {\"type\": \"Odontologia\", \"Doctor\": \"Adolfo Jun\"},\n" +
                "    ]\n" +
                "  }\n" +
                "}}";
        return response;
    }
}
