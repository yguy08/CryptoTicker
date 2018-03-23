package com.tickercash.window;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class ClientStarter {
	
    private MessageConsumer consumer;
    
    Session session;
    
    Destination destination;
    
    private static ExceptionListener listener = new ExceptionListener() {

        @Override
        public void onException(JMSException arg0) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }
        
    };
    
    public ClientStarter() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic("GLOBAL");
        consumer = session.createConsumer(destination);
    }
    
    public String nextMessage() throws JMSException {
            Message message = consumer.receive();

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                return text;
            } else {
                return null;
            }
    }

    public static void main(String[] args) throws JMSException {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        connection.setExceptionListener(listener);

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createTopic("BTC/USD:Fake");

        // Create a MessageConsumer from the Session to the Topic or Queue

    }

}
