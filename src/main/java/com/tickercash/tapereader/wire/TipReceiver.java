package com.tickercash.tapereader.wire;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.clerk.TipClerk;
import com.tickercash.tapereader.event.MarketDataTranslator;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.tip.TipEngine;
import com.tickercash.tapereader.util.DisruptorFactory;

public class TipReceiver implements MessageListener, EventHandler<Tick>, UpdateListener {
    
    private MessageConsumer consumer;
    
    private Session session;
    
    private Destination destination;
    
    private Disruptor<Tick> disruptor;
    
    private RingBuffer<Tick> ringBuffer;
    
    private TipEngine tip;
    
    private TipClerk clerk;
    
    //We need clerk to send onTick to
    @SuppressWarnings("unchecked")
	public TipReceiver(TipClerk clerk, TipEngine tipEngine) throws Exception {
        disruptor = DisruptorFactory.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        this.clerk = clerk;
        this.tip = tipEngine;
        this.tip.addListener(this);
        disruptor.handleEventsWith(this);
        
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(clerk.getTipName());
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
                System.out.println("RING BUFFER");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

	@Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        tip.sendNewTick(event);
        System.out.println("TIP RECEIVER:  "+event);
	}

	@Override
	public void update(EventBean[] newTick, EventBean[] oldEvents) {
        String symbol = (String) newTick[0].get("symbol");
        String feed = (String) newTick[0].get("feed");
        long timestamp = (Long) newTick[0].get("timestamp");
        double last = (Double) newTick[0].get("last");
        System.out.println(String.format("UPDATE LISTENER: Symbol: %s, Feed: %s, Timestamp: %d, Last: %f", symbol, feed, timestamp, last));
        clerk.onTick(new Tick(symbol, feed, timestamp, last));
	}
}
