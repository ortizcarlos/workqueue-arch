/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.core;

import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.JobCompletionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 *
 * @author Carlos
 */
@Component
public class JobExecutionPublisherDefaultImpl implements JobExecutionPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutionPublisherDefaultImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${work.queue.output}")
    private String queueOut;
            
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publish(final JobCompletionEvent jobCompletionEvent) {
        LOGGER.info("Start sending message...!");
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String textMessage = "{\"job-id\": \""
                        + jobCompletionEvent.getJobId() + "\"}";
               
                try {
                    textMessage = objectMapper.writeValueAsString(jobCompletionEvent);
                } catch (JsonProcessingException ex) {
                    LOGGER.error("Error convirtiendo status del reporte a JSON");
                }

                LOGGER.info("Sending message " + textMessage);

                return session.createTextMessage(textMessage);
            }
        };
        LOGGER.info("Sending message.");
        jmsTemplate.send(queueOut, messageCreator);
    }
}
