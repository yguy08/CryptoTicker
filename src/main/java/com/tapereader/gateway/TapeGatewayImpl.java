package com.tapereader.gateway;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.tapereader.config.ActiveMQBrokerURL;
import com.tapereader.config.TopicName;
import com.tapereader.framework.TapeGateway;

public class TapeGatewayImpl implements TapeGateway, MessageListener {
    
    private final String BROKER_URL;
    private final String UPDATE_TOPIC_NAME;
    private Connection connection;
    private MessageConsumer updateConsumer;
    private EventBus eventBus;
    
    @Inject
    private TapeGatewayImpl(@ActiveMQBrokerURL String brokerURL, @TopicName String topicName){
        BROKER_URL = brokerURL;
        UPDATE_TOPIC_NAME = topicName;
    }

    @Override
    public void initialize(Object object) throws Exception {
        eventBus = new EventBus();
        eventBus.register(object);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination updateTopic = session.createTopic(UPDATE_TOPIC_NAME);
        updateConsumer = session.createConsumer(updateTopic);
        updateConsumer.setMessageListener(this);
    }

    @Override
    public void read() throws Exception {
        connection.start();
    }

    @Override
    public void detach() throws Exception {
        if (connection != null) {
            connection.stop();
            connection.close();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMsg = (TextMessage) message; // assume cast always works
            String newState = textMsg.getText();
            update(newState);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    private void update(String newState) throws JMSException {
        eventBus.post(newState);
    }
    
    //Add disruptor like transmitter and then have addEventHandler and listen for a bunch of events?

}
