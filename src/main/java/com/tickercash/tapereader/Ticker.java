package com.tickercash.tapereader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventTranslatorThreeArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public abstract class Ticker implements ITicker {
        
    protected static final int BUFFER = 1024;
    
    protected Disruptor<Tick> disruptor;
    
    protected RingBuffer<Tick> ringBuffer;

    protected List<String> subscriptions;

    public Ticker() {
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
    public void addTape(ITip tip) {
        disruptor.handleEventsWith(tip);
    }  
    
    protected static final EventTranslatorThreeArg<Tick,String,Long,Double> TRANSLATOR =
            new EventTranslatorThreeArg<Tick,String,Long,Double>() {
        @Override
        public void translateTo(Tick event, long sequence, String symbol, Long timestamp, Double last) {
            event.set(symbol, timestamp, last);
        }                
    };

    protected void onTick(String symbol, long timestamp, double last){
        ringBuffer.publishEvent(TRANSLATOR, symbol, timestamp, last);
    }

}
