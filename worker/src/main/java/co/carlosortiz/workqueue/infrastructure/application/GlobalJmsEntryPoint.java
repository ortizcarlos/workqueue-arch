/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.infrastructure.application;

import co.carlosortiz.workqueue.core.job.GlobalJobController;
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
public class GlobalJmsEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalJmsEntryPoint.class);
    @Autowired
    private GlobalJobController globalJobController;

    @JmsListener(destination = "${work.queue.input}",
            containerFactory = "jmsQueueListenerContainerFactory")
    public void receiveAndProcessMessage(String message) {
        LOGGER.info("message to process: [{}]", message);
        globalJobController.processJob(message);
    }
}
