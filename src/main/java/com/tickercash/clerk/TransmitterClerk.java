package com.tickercash.clerk;

import com.lmax.disruptor.EventHandler;
import com.tickercash.marketdata.MarketEvent;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TransmitterClerk implements EventHandler<MarketEvent> {

	  private static String clientId;
	  private static Connection connection;
	  private static Session session;
	  private static MessageProducer messageProducer;
	  
	  public TransmitterClerk() throws Exception {
		    clientId = "TransmitterClerk";
	
		    // create a Connection Factory
		    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
	
		    // create a Connection
		    connection = connectionFactory.createConnection();
		    connection.setClientID(clientId);
	
		    // create a Session
		    session =
		        connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    
		    // create the Topic to which messages will be sent
		    Topic topic = session.createTopic("GLOBAL");

		    // create a MessageProducer for sending messages
		    messageProducer = session.createProducer(topic);
	  }

	public static void closeConnection() throws JMSException {
		connection.close();
	}

	@Override
	public void onEvent(MarketEvent event, long sequence, boolean endOfBatch) throws Exception {
		
		try{

		    TextMessage textMessage = session.createTextMessage(event.get().toString());
		    
		    messageProducer.send(textMessage);
		    
			System.out.println("MESSAGE:"+event.get().toString());
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}
	
}
