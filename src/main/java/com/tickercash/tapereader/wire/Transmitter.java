package com.tickercash.tapereader.wire;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.marketdata.Tick;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transmitter implements EventHandler<Tick> {
      
	private Connection connection;
	  
	private Session session;
	  
	private MessageProducer messageProducer;
	  
	private static final Logger LOGGER = LogManager.getLogger("Transmitter");
	  
	public Transmitter(String topicStr) throws Exception {
	      MQBroker.initDefaultMQBroker();
	      connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
	      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	      messageProducer = session.createProducer(session.createTopic(topicStr));
	}

	public void closeConnection() throws JMSException {
	    connection.close();
	}
	
	@Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
	    try{
	        messageProducer.send(session.createTextMessage(event.toString()));
	    }catch(Exception e){
	        LOGGER.error(e.getMessage());
	    }
	}    
}