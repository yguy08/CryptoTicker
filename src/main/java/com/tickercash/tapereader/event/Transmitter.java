package com.tickercash.tapereader.event;

import com.lmax.disruptor.EventHandler;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transmitter implements EventHandler<Tick> {
      
      private static Connection connection;
      
      private static Session session;
      
      private static MessageProducer messageProducer;
      
      private static BrokerService broker;
      
      private static Topic topic;
      
      private static TextMessage textMessage;
      
      private static final Logger LOGGER = LogManager.getLogger("Transmitter");
      
      public Transmitter(String topicStr) throws Exception {
            broker = new BrokerService();
            broker.addConnector("tcp://localhost:61616");
            broker.setDeleteAllMessagesOnStartup(true);
            broker.start();

            // create a Connection Factory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // create a Connection
            connection = connectionFactory.createConnection();

            // create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // create the Topic to which messages will be sent
            topic = session.createTopic("GLOBAL");

            // create a MessageProducer for sending messages
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