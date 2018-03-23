package com.tickercash.clerk;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.event.handler.TipSender;
import com.tickercash.event.translator.MarketDataTranslator;
import com.tickercash.marketdata.Tick;
import com.tickercash.tip.TipEngine;

@SuppressWarnings({"unchecked"})
public class Receiver implements MessageListener {
    
    private MessageConsumer consumer;
    
    private Session session;
    
    private Destination destination;
    
    private Disruptor<Tick> disruptor;
    
    private RingBuffer<Tick> ringBuffer;
    
    public Receiver(String topic, TipEngine tip) throws Exception {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(new TipSender(tip));
        
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(topic);
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }
    
    public void start(){
        disruptor.start();
    }

    @Override
    public void onMessage(Message msg) {
        try{
            if(msg instanceof TextMessage){
                ringBuffer.publishEvent(MarketDataTranslator::translateTo, ((TextMessage) msg).getText());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
