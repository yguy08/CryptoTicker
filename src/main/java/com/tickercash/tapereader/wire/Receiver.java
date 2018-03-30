package com.tickercash.tapereader.wire;

import java.nio.ByteBuffer;

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
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.util.DisruptorFactory;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.StopCharTesters;

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
            ringBuffer.publishEvent(TRANSLATOR, ((TextMessage) msg).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    private static final EventTranslatorOneArg<Tick, String> TRANSLATOR = new EventTranslatorOneArg<Tick, String>(){
        
        private final Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        
        @Override
        public void translateTo(Tick event, long sequence, String tick) {
            bytes.clear().append8bit(tick);
            event.set(bytes.parseUtf8(StopCharTesters.SPACE_STOP), bytes.parseUtf8(StopCharTesters.SPACE_STOP), bytes.parseLong(), bytes.parseDouble());
        }
        
    };
}
