package co.carlosortiz.workqueue.workers.units.job;

import co.carlosortiz.workqueue.workers.units.pipelines.JobPipeline;
import co.carlosortiz.workqueue.workers.units.processors.JobProcessor;
import co.carlosortiz.workqueue.workers.units.reportes.impl.ReportProcessorImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class GlobalJobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalJobController.class);

    private ExecutorService jobExecutorService;

    @Autowired
    private JobCompletionHandler jobCompletionHandler;

    private static final int MAXTHREADS = 5;

    private int maxthreads = MAXTHREADS;

    @Autowired
    private JobExecutorInstanceBuilder jobExecutorInstanceBuilder;


    GlobalJobController(){
        jobExecutorService = Executors.newFixedThreadPool(maxthreads);
    }

    /*
         {
            "job-id": "7b51aa48-36a9-4c07-adbb-bf68f1c7bc97",
            "job-code": "appointment-schedule",
            "user": "jhondoe",
            "timestamp" : "",
            "job-params": [
                {
                    "param-name": "ips",
                    "param-value": "camino"
                },
                {
                    "param-name": "requested-appointment-date",
                    "param-value": "2019-09-01-11.00.00.000"
                }
            ]
        }


 */
    public void processJob(String rawJobMsg) {
       JobRequest jobRequest = createJobRequest(rawJobMsg);
       JobPipeline jobPipeline = jobExecutorInstanceBuilder.getJobExecutorInstance(jobRequest.getJobCode());

       JobProcessor jobProcessor = jobPipeline.getProcessor();
       jobProcessor.setParams(jobRequest.getJobParams());
       Future<Callable> completion =  jobExecutorService.submit(jobProcessor);
       jobCompletionHandler.submitJobCompletion(new JobExecution(completion,jobRequest,System.currentTimeMillis()),jobPipeline);
    }

    private JobRequest createJobRequest(String rawJobMsg) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rawJobMsg);
            String jobId = root.path("jobId").asText();
            String jobCode = root.path("jobCode").asText();
            String user = root.path("user").asText();

            LOGGER.info("job-code: [{}]" , jobCode);
            LOGGER.info("job-id: [{}]" , jobId);
            LOGGER.info("job-user: [{}]" , user);

            //Obtiene los parametros del reporte
            Map<String, String> jobParams
                    = mapJobParams(root.path("params"));

            return new JobRequest(jobId,user,jobCode,jobParams);

        } catch (IOException ioe) {
           throw new IllegalArgumentException("Invalid job request");
        }
    }

    private Map<String, String> mapJobParams(JsonNode paramsNode) {
        Map<String, String> reportParams = new HashMap();
        for (JsonNode node : paramsNode) {
            String paramName = node.path("name").asText();
            String paramValue = node.path("value").asText();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("param-name [{}] " ,paramName);
                LOGGER.debug("param-value [{}] " , paramValue);
            }
            reportParams.put(paramName, paramValue);
        }
        return reportParams;
    }

}
