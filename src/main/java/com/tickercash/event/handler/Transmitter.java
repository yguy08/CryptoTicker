package com.tickercash.event.handler;

import com.lmax.disruptor.EventHandler;
import com.tickercash.event.MarketDataEvent;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class Transmitter implements EventHandler<MarketDataEvent> {

      private static String clientId;
      private static Connection connection;
      private static Session session;
      private static MessageProducer messageProducer;
      private static BrokerService broker;
      
      public Transmitter() throws Exception {
            clientId = "TransmitterClerk";
            
            broker = new BrokerService();
            broker.addConnector("tcp://localhost:61616");
            
            broker.start();
    
            // create a Connection Factory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
    
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
    public void onEvent(MarketDataEvent event, long sequence, boolean endOfBatch) throws Exception {
        
        try{

            TextMessage textMessage = session.createTextMessage(event.toString());
            
            messageProducer.send(textMessage);
            
            System.out.println("MESSAGE:"+event.get().toString());
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    
}