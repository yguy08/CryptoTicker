package com.tickercash.clerk;

import java.util.Random;

import com.tickercash.marketdata.MarketEvent;
import com.tickercash.util.TapeLogger;
import com.tickercash.util.UniqueCurrentTimeMS;

public class FakeTicker extends LiveDataClerk {
    
    private static final Random random = new Random();

    @Override
    public void start() {
        disruptor.start();
        while(true){
            ringBuffer.publishEvent(MarketEvent.TRANSLATOR_SYMBOL_TS_LAST::translateTo, 
            		"FAKE/BTC",UniqueCurrentTimeMS.uniqueCurrentTimeMS(), random.nextDouble());
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                TapeLogger.getLogger().error(e.getMessage());
            }
        }        
    }

}
