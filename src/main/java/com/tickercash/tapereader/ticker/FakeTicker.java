package com.tickercash.tapereader.ticker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.clerk.DisruptorClerk;
import com.tickercash.tapereader.tick.Tick;
import com.tickercash.tapereader.tick.handler.TickHandler;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

public class FakeTicker implements Ticker {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    @SuppressWarnings("unchecked")
    @Inject
    protected FakeTicker(TickHandler handler) {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(handler::onTick);
        disruptor.start();
    }

    @Override
    public void transmit() throws Exception {
        running.set(true);
        while(running.get()) {
            ringBuffer.publishEvent(this::translateTo);
            Thread.sleep(1000);
        }
    }
    
    private final void translateTo(Tick event, long sequence){
        event.set(new Tick("BTC/USD", "FAKE", UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 10000.00));
    }

}
