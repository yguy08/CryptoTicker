package com.tickercash.tapereader.ticker;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.framework.EventHandler;
import com.tickercash.tapereader.framework.Ticker;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.util.DisruptorClerk;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

@SuppressWarnings({ "unchecked" })
public class FakeTicker implements Ticker {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;    
    
    @Inject
    protected FakeTicker(EventHandler handler) {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(handler::onEvent);
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
