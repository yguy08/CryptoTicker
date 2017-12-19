package com.tickercash.tapereader.event.handler;

import java.net.URI;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.event.Tick;

public class Publish extends TickEventHandler {
	
	private static final Logger LOGGER = LogManager.getLogger("Publish");
	
	BrokerService broker;
	
	ActiveMQConnectionFactory connectionFactory;
	
	Connection connection;
	
	Session session;
	
	Destination destination;
	
	MessageProducer producer;

	@Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
		producer.send(session.createTextMessage(event.toString()));
		//LOGGER.info("Message sent: {}",event);		
	}

	@Override
	public void configure() throws Exception {
		
        broker = BrokerFactory.createBroker(new URI(
                "broker:(tcp://localhost:61616)"));
		broker.start();
		
        // Create a ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        destination = session.createQueue("TEST.FOO");

        // Create a MessageProducer from the Session to the Topic or Queue
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
	}

}
