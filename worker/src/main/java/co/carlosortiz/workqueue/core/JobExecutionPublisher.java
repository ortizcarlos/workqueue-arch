/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.core;

import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.JobCompletionEvent;

/**
 *
 * @author Carlos
 */
public interface JobExecutionPublisher {
  void publish(final JobCompletionEvent jobCompletionEvent);
}
