/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.mensajes;

import co.carlosortiz.workqueue.aplicacion.servicios.JobResultCoordinator;
import co.carlosortiz.workqueue.aplicacion.servicios.ReportResultProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
            String result = getResponse(root.path("responseMap"));

            LOGGER.info("job-code: [{}]", jobCode);
            LOGGER.info("job-id: [{}]", jobId);
            LOGGER.info("job-response: [{}]", result);
            
            this.jobResultCoordinator.processJobCompleted (jobId, result);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error procesando resultado" , e);
        }
    }

    private String getResponse(JsonNode responseMap) {

            String result = responseMap.path("result").asText();
         return result;
    }
}
