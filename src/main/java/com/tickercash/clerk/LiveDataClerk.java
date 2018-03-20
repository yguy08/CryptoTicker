package com.tickercash.clerk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tickercash.marketdata.MarketEvent;

public abstract class LiveDataClerk {
        
    protected static final int BUFFER = 1024;
    
    protected final Disruptor<MarketEvent> disruptor;
    
    protected final RingBuffer<MarketEvent> ringBuffer;

    protected final List<String> subscriptions;

    public LiveDataClerk() {
        disruptor = new Disruptor<MarketEvent>(MarketEvent::new, BUFFER, Executors.defaultThreadFactory(),
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
    public void addHandler(EventHandler<MarketEvent>... handler) {
        disruptor.handleEventsWith(handler);
    }
        
    protected void translateTo(MarketEvent push, long sequence, MarketEvent pull) {
    	push.set(pull);
    }

    protected void onTick(MarketEvent c){
        ringBuffer.publishEvent(this::translateTo, c);
    }
    
    public abstract void start() throws Exception;

}
