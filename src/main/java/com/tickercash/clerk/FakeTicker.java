package com.tickercash.clerk;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.tickercash.enums.QuoteBoyType;
import com.tickercash.event.translator.MarketDataTranslator;
import com.tickercash.util.UniqueCurrentTimeMS;

public class FakeTicker extends QuoteBoy {
    
    private static final List<String> SYMBOLS = Arrays.asList("BTC/USD","ETH/BTC","XRP/BTC","BCH/BTC","LTC/BTC");
    
    private static final Random random = new Random();

    private static String symbol;
    
    @Override
    public void start() throws Exception {
        disruptor.start();
        
        Runnable task = () -> {
            try{
                symbol = SYMBOLS.get(random.nextInt(SYMBOLS.size()));
                ringBuffer.publishEvent(MarketDataTranslator::translateTo, 
                        new String[]{symbol,QuoteBoyType.FAKE.name()}, 
                        UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                        symbol.endsWith("USD") ? random.nextInt(10000) : random.nextDouble());
            }catch(Exception e){
                e.printStackTrace();
            }
        };
        executor.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public String getName() {
        return QuoteBoyType.FAKE.toString();
    }

}
