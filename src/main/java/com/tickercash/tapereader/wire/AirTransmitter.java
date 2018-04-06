package com.tickercash.tapereader.wire;

import com.tickercash.tapereader.marketdata.Tick;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirTransmitter implements Transmitter {
      
    private Connection connection;
      
    private Session session;
      
    private MessageProducer messageProducer;
      
    private static final Logger LOGGER = LogManager.getLogger("Transmitter");
      
    public AirTransmitter(String topicStr) throws Exception {
          MQBroker.initDefaultMQBroker();
          connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
          session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
          messageProducer = session.createProducer(session.createTopic(topicStr));
          messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
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
