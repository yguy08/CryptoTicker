package com.speculation1000.cryptoticker.disruptor;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.speculation1000.cryptoticker.marketdataevent.Tick;

public class TickEventHandler implements EventHandler<Tick>{
	
	private static final Logger logger = LogManager.getLogger("TickEventHandler");
	
	private static ActiveMQConnectionFactory connectionFactory;
	
	private static Connection connection;
	
	private static Session session;
	
	private static Destination destination;
	
	private static MessageProducer producer;
	
	public TickEventHandler(String name) {
        
		try {
            // Create a ConnectionFactory
            connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            destination = session.createQueue("TEST.FOO");

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
	}
	
	
    @Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch){
    	try {
        	producer.send(session.createTextMessage(event.toString()));
    	}catch(Exception e) {
    		logger.error(e.getMessage());
    	}
    }
}
