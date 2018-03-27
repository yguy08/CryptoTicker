package com.tickercash.tapereader.clerk;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.tickercash.tapereader.event.MarketDataTranslator;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

public class FakeQuoteBoy extends QuoteBoy {
    
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
        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public String getTopicName() {
        return QuoteBoyType.FAKE.toString();
    }

}
