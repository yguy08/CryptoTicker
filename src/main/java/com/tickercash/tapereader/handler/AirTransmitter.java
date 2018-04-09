package com.tickercash.tapereader.handler;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tickercash.tapereader.framework.MQBroker;
import com.tickercash.tapereader.framework.Transmitter;
import com.tickercash.tapereader.model.Tick;

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
    
    @Inject
    public AirTransmitter(@Named("ActiveMQBrokerURL") String brokerURL) throws Exception {
        MQBroker.initDefaultMQBroker();
        connection = new ActiveMQConnectionFactory(brokerURL).createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(session.createTopic("FAKE"));
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
