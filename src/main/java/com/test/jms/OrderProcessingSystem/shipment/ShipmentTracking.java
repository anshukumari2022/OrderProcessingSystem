package com.test.jms.OrderProcessingSystem.shipment;

import com.test.jms.OrderProcessingSystem.shipment.listners.ShipmentTrackingListener;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ShipmentTracking {
    public static void main(String[] args) throws NamingException, InterruptedException {
        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {

            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            consumer.setMessageListener(new ShipmentTrackingListener());

            Thread.sleep(20000);
        };

    }
}
