package com.tickercash.clerk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tickercash.marketdata.Tick;

public abstract class QuoteBoy {
        
    protected static final int BUFFER = 1024;
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;

    protected final List<String> subscriptions;

    public QuoteBoy() {
        disruptor = new Disruptor<Tick>(Tick::new, BUFFER, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new BlockingWaitStrategy());
        ringBuffer = disruptor.getRingBuffer();
        subscriptions = new ArrayList<>();
    }
    
    public void subscribe(String symbol) {
        subscriptions.add(symbol);      
    }
    
    public void unsubscribe(String symbol) {
        subscriptions.removeIf((s) -> s.equals(symbol));        
    }
    
    @SuppressWarnings("unchecked")
    public void addHandler(EventHandler<Tick>... handler) {
        disruptor.handleEventsWith(handler);
    }
        
    protected void translateTo(Tick event, long sequence, String symbol, Long timestamp, Double last) {
    	event.set(symbol, timestamp, last);
    }

    protected void onTick(String symbol, long timestamp, double last){
        ringBuffer.publishEvent(this::translateTo, symbol, timestamp, last);
    }
    
    public abstract void start();

}
