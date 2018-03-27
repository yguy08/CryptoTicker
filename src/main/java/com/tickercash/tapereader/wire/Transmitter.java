package com.tickercash.tapereader.wire;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.model.Tick;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transmitter implements EventHandler<Tick> {
      
	  private static Connection connection;
	  
	  private static Session session;
	  
	  private static MessageProducer messageProducer;
	  
	  private static Topic topic;
	  
	  private static TextMessage textMessage;
	  
	  private static final Logger LOGGER = LogManager.getLogger("Transmitter");
	  
	  public Transmitter(String topicStr) throws Exception {
	        MQBroker.initDefaultMQBroker();
	        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	        connection = connectionFactory.createConnection();
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        topic = session.createTopic("GLOBAL");
	        messageProducer = session.createProducer(topic);
	  }

	public static void closeConnection() throws JMSException {
	    connection.close();
	}
	
	@Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
	    try{
	        textMessage = session.createTextMessage(event.toString());
	        messageProducer.send(textMessage);
	        LOGGER.info("MESSAGE:"+topic.getTopicName()+" "+event.toString());
	    }catch(Exception e){
	        LOGGER.error(e.getMessage());
	    }
	}
    
}