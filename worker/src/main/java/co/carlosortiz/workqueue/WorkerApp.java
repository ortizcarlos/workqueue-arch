/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue;

import co.carlosortiz.workqueue.infrastructure.message.config.JmsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;



@SpringBootApplication
@Import(value = { JmsConfig.class })
public class WorkerApp {

	public static void main(String[] args) {
		SpringApplication.run(WorkerApp.class, args);
	}
}
