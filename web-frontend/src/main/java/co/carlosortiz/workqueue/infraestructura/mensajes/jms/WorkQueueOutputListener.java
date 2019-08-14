/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.infraestructura.mensajes.jms;

import co.carlosortiz.workqueue.core.workqueue.JobResultCoordinator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class WorkQueueOutputListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkQueueOutputListener.class);

    @Autowired
    private JobResultCoordinator jobResultCoordinator;

    @JmsListener(destination = "${work.queue.input}",
            containerFactory = "jmsQueueListenerContainerFactory")
    public void receiveAndProcessMessage(String message) {
        LOGGER.info("New Message read from ouput WorkQueue [{}]", message);
        //Recibe mensaje de procesamiento de un reporte y notifia al cliente
        //el resultado de la ejeucion.

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(message);
            String jobId = root.path("jobId").asText();
            String jobCode = root.path("jobCode").asText();
            String user = root.path("user").asText();
            String jobStatus = root.path("status").asText();
            String jobStatusMsg =  root.path("statusMsg").asText();
            String result = getResponse(root.path("responseMap"));

            LOGGER.info("job-code: [{}]", jobCode);
            LOGGER.info("job-id: [{}]", jobId);
            LOGGER.info("job-status: [{}]", jobStatus);
            LOGGER.info("job-statusMsg: [{}]", jobStatusMsg);
            LOGGER.info("job-response: [{}]", result);
            
            this.jobResultCoordinator.processJobCompleted (jobId, jobStatus, result);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error procesando resultado" , e);
        }
    }

    private String getResponse(JsonNode responseMap) {

            String result = responseMap.path("result").asText();
         return result;
    }
}
