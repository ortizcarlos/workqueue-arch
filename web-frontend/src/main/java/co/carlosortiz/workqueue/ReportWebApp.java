/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue;

import co.carlosortiz.workqueue.infraestructura.mensajes.jms.config.JmsConfig;
import co.carlosortiz.workqueue.infraestructura.web.config.StaticResourceConfiguration;
import co.carlosortiz.workqueue.infraestructura.web.config.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Carlos
 */
@SpringBootApplication
@Import(value = { JmsConfig.class , StaticResourceConfiguration.class , WebSocketConfig.class })
public class ReportWebApp {
    
     public static void main(String[] args) {
        SpringApplication.run(ReportWebApp.class, args);
    }
}
