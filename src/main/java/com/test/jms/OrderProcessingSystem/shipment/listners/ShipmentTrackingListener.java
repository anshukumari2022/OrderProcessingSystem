package com.test.jms.OrderProcessingSystem.shipment.listners;

import com.test.jms.OrderProcessingSystem.model.OrderDetail;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ShipmentTrackingListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext()) {

            InitialContext initialContext = new InitialContext();
            Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

            MapMessage replyMessage = jmsContext.createMapMessage();
            OrderDetail order = (OrderDetail) objectMessage.getObject();

            System.out.println("Received order: " + order.getOrderId());

            if(order.getTotalAmount()>0) {
                replyMessage.setBoolean("Received", true);
            }else {
                replyMessage.setBoolean("Received", false);
            }

            JMSProducer producer = jmsContext.createProducer();
            producer.send(replyQueue, replyMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
