package com.tapereader.ticker;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.CounterHandler;
import com.tapereader.framework.DisruptorClerk;
import com.tapereader.framework.Tick;
import com.tapereader.framework.Ticker;
import com.tapereader.framework.Transmitter;
import com.tapereader.util.UniqueCurrentTimeMS;

public class FakeTicker implements Ticker {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);
    
    private final Disruptor<Tick> disruptor;
    
    private final RingBuffer<Tick> ringBuffer;
    
    @SuppressWarnings("unchecked")
    @Inject
    protected FakeTicker(Transmitter transmitter) {
    	disruptor = DisruptorClerk.newTickDisruptor();
    	ringBuffer = disruptor.getRingBuffer();
    	disruptor.handleEventsWith(transmitter::transmit, new CounterHandler());
        disruptor.start();
    }

    @Override
    public void start() throws Exception {
        running.set(true);
        while(running.get()) {
            ringBuffer.publishEvent(this::translateTo);
            Thread.sleep(1000);
        }
    }
    
    private final void translateTo(Tick event, long sequence){
        event.set("BTC/USD", "FAKE", UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 10000.00);
    }

}
