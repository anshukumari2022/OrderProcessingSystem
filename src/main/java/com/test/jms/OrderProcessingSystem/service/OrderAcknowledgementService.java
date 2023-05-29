package com.test.jms.OrderProcessingSystem.service;

import com.test.jms.OrderProcessingSystem.model.OrderDetail;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Service
public class OrderAcknowledgementService {
    public String createOrder(OrderDetail order) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {

            JMSProducer producer = jmsContext.createProducer();

            ObjectMessage objectMessage = jmsContext.createObjectMessage();
            objectMessage.setObject(order);

            producer.send(requestQueue, objectMessage);

            JMSConsumer consumer = jmsContext.createConsumer(replyQueue);
            MapMessage replyMessage = (MapMessage) consumer.receive(30000);

            String message = "Order placed status :" + replyMessage.getBoolean("Received");
            return message;
        }
    }
}
