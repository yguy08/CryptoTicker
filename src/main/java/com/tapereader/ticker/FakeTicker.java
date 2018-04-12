package com.tapereader.ticker;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.Tape;
import com.tapereader.framework.Ticker;
import com.tapereader.model.Tick;
import com.tapereader.util.DisruptorClerk;
import com.tapereader.util.UniqueCurrentTimeMS;

@SuppressWarnings({ "unchecked" })
public class FakeTicker implements Ticker {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;    
    
    @Inject
    protected FakeTicker(Tape tape) {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(tape::onEvent);
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
