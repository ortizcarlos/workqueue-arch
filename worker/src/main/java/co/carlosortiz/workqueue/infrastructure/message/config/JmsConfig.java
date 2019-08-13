/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.infrastructure.message.config;

import javax.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

/**
 *
 * @author Carlos
 */
@Configuration
@EnableJms
public class JmsConfig {

    @Value("${jms.cache.size}")
    private int jmsCacheSize;

    @Bean
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(ConnectionFactory connectionFactory) {
        
        String jmsClientID = "worker-queue-" + System.currentTimeMillis();
        
        CachingConnectionFactory ccf = new CachingConnectionFactory(connectionFactory);
        ccf.setClientId(jmsClientID);
        ccf.setSessionCacheSize(jmsCacheSize);

        DefaultJmsListenerContainerFactory dmlc = new DefaultJmsListenerContainerFactory();
        dmlc.setConnectionFactory(ccf);
        System.out.println("ACTIVMQ STARTED");
        
        return dmlc;
    }
}
