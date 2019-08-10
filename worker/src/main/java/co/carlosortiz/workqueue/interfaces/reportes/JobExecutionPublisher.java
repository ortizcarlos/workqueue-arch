/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes;

import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.JobCompletionEvent;
import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.ReportProcessedEvent;
import co.carlosortiz.workqueue.workers.units.job.JobCompletionHandler;

/**
 *
 * @author Carlos
 */
public interface JobExecutionPublisher {
  void publish(final JobCompletionEvent jobCompletionEvent);
}
