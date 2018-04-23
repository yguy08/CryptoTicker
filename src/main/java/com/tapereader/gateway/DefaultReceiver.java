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
import com.tapereader.framework.MarketEvent;
import com.tapereader.framework.Receiver;

import net.openhft.chronicle.wire.Marshallable;

public class DefaultReceiver implements Receiver, MessageListener {
    
    private final String BROKER_URL;
    private final String UPDATE_TOPIC_NAME;
    private Connection connection;
    private MessageConsumer updateConsumer;
    private EventBus eventBus;
        
    @Inject
    private DefaultReceiver(@ActiveMQBrokerURL String brokerURL, @TopicName String topicName){
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
            TextMessage textMsg = (TextMessage) message;
            String newState = textMsg.getText();
            update(newState);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    private void update(String newState) throws JMSException {
        MarketEvent event = Marshallable.fromString(newState);
        Object t = Marshallable.fromString(event.get().toString());
        eventBus.post(t);
    }

}
