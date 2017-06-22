package com.kwetter.JMS;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by hein on 5/24/17.
 */
@MessageDriven(name= "dlqmdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/kwetterDLQ"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ActiveMQ.DLQ"),
        @ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar-5.14.5"),
})
public class DeadLetterQueueMessageBean implements MessageListener{

    @Override
    public void onMessage(Message message) {
        System.out.println("A new message has arrived in the Dead Letter Queue:");
        System.out.println("New message: " + message);
    }
}
