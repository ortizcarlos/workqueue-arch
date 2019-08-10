/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.core.workqueue;

import co.carlosortiz.workqueue.core.workqueue.JobRequest;


/**
 *
 * @author Carlos
 */
public interface JobRequestPublisher {
  void publish(final JobRequest jobRequest);
}
