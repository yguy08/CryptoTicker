package com.tapereader.ticker;

import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.Transmitter;
import com.tapereader.model.Tick;
import com.tapereader.util.UniqueCurrentTimeMS;

public class FakeTicker extends AbstractTicker {
    
    @Inject
    protected FakeTicker(Transmitter transmitter) {
        super(transmitter);
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

    @Override
    public void handleEventsWith(Disruptor disruptor) {
        disruptor.handleEventsWith(transmitter::transmit);
    }
    
    private final void translateTo(Tick event, long sequence){
        event.set("BTC/USD", "FAKE", UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 10000.00);
    }
}
