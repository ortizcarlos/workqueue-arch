/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes;

import co.carlosortiz.workqueue.workers.units.job.GlobalJobController;
import co.carlosortiz.workqueue.workers.units.reportes.ReportProcessor;
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
public class ReportQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportQueueListener.class);
    @Autowired
    //private ReportProcessor reportProcessor;
    private GlobalJobController globalJobController;

    @JmsListener(destination = "${work.queue.input}",
            containerFactory = "jmsQueueListenerContainerFactory")
    public void receiveAndProcessMessage(String message) {
        LOGGER.info("message to process: [{}]", message);
        globalJobController.processJob(message);
    }
}
