/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes;

import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.ReportProcessedEvent;

/**
 *
 * @author Carlos
 */
public interface ReportExecutionPublisher {
  void publish(final ReportProcessedEvent reportProcessedEvent);
}
