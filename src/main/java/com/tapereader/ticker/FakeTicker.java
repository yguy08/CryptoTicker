package com.tapereader.ticker;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Inject;
import com.tapereader.framework.Tape;
import com.tapereader.model.Tick;
import com.tapereader.util.UniqueCurrentTimeMS;

public class FakeTicker extends AbstractTicker {
    
    protected final AtomicBoolean running = new AtomicBoolean(false);   
    
    @Inject
    protected FakeTicker(Tape tape) {
        super(tape);
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
