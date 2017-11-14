package com.speculation1000.cryptoticker.poloniex;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.core.ITickConsumer;

public class Consumer implements ITickConsumer, ExceptionListener {
	
	private static final Logger logger = LogManager.getLogger("Consumer");
	
	private ActiveMQConnectionFactory connectionFactory;
	
	private Connection connection;
	
	private Session session;
	
	private Destination destination;
	
	private MessageConsumer consumer;
	
	public Consumer() {
        try {
       	 
            // Create a ConnectionFactory
            connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            destination = session.createQueue("TEST.FOO");

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createConsumer(destination);
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
	}
	

	@Override
    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }

	@Override
	public void consume() throws InterruptedException, Exception {
        // Wait for a message
        Message message = consumer.receive(1000);

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            logger.info("Received: " + text);
        } else {
            logger.info("Received: " + message);
        }

	}

}
