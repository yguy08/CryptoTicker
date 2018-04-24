package com.tapereader.framework;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import com.google.inject.Inject;
import com.tapereader.config.ActiveMQBrokerURL;
import com.tapereader.config.TopicName;

public class DefaultTransmitter implements Transmitter {
    
    private final String BROKER_URL;
    private final String UPDATE_TOPIC_NAME;
    private Connection connection;
    private Session session;
    private MessageProducer updateProducer;
    
    @Inject
    private DefaultTransmitter(@ActiveMQBrokerURL String brokerURL, @TopicName String topicName) throws Exception {
        BROKER_URL = brokerURL;
        UPDATE_TOPIC_NAME = topicName;
        initialize();
    }
    
    private void initialize() throws Exception {
        try{
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(BROKER_URL);
            broker.start();
        }catch (Exception e){
            
        }
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination updateTopic = session.createTopic(UPDATE_TOPIC_NAME);
        updateProducer = session.createProducer(updateTopic);

        connection.start();
    }
    
    @Override
    public void transmit(String event) throws Exception {
        TextMessage message = session.createTextMessage(event.toString());
        updateProducer.send(message);
    }

    @Override
    public void endTransmission() throws Exception {
        if (connection != null) {
            connection.stop();
            connection.close();
        }
    }

    @Override
    public void transmit(Object event, long sequence, boolean endOfBatch) throws Exception {
        TextMessage message = session.createTextMessage(event.toString());
        updateProducer.send(message);
    }
}
