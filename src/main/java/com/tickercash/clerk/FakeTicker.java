package com.tickercash.clerk;

import com.tickercash.event.translator.MarketDataTranslator;

public class FakeTicker extends QuoteBoy {

    @Override
    public void start() throws Exception {
        disruptor.start();
        while(true){
            ringBuffer.publishEvent(MarketDataTranslator::translateToFakeTick);
            Thread.sleep(1000);
        }
    }

}
