/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.core.job.event;

import co.carlosortiz.workqueue.core.job.event.JobCompletionEvent;

/**
 *
 * @author Carlos
 */
public interface JobCompletionPublisher {
  void publish(final JobCompletionEvent jobCompletionEvent);
}
