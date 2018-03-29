package com.tickercash.tapereader.wire;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.event.MarketDataTranslator;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.util.DisruptorFactory;

public class Receiver implements MessageListener {
    
    private MessageConsumer consumer;
    
    private Session session;
    
    private Destination destination;
    
    private Disruptor<Tick> disruptor;
    
    private RingBuffer<Tick> ringBuffer;
    
	public Receiver(String topicName) throws Exception {
        disruptor = DisruptorFactory.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        initConnection(topicName);
    }
	
	private void initConnection(String topicName) throws Exception {
	    MQBroker.initDefaultMQBroker();
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(topicName);
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
	}
    
    public void startReceiving(){
        disruptor.start();
    }
    
    @SuppressWarnings({ "unchecked" })
    public void setEventHandler(EventHandler<Tick> handler) {
    	disruptor.handleEventsWith(handler);
    }

    @Override
    public void onMessage(Message msg) {
        try {
            ringBuffer.publishEvent(MarketDataTranslator::translateTo, ((TextMessage) msg).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
