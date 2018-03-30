package com.tickercash.tapereader.clerk;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

public class FakeQuoteBoy extends QuoteBoy {
    
    private static final List<String> SYMBOLS = Arrays.asList("BTC/USD","ETH/BTC","XRP/BTC","BCH/BTC","LTC/BTC");
    
    private static final Random random = new Random();

    private static String symbol;
    
    @Override
    public void start() throws Exception {
        disruptor.start();
        running.set(true);
        while(running.get()) {
            ringBuffer.publishEvent(this::translateTo);
            Thread.sleep(throttle);
        }
    }

    @Override
    public String getTopicName() {
        return QuoteBoyType.FAKE.toString();
    }
    
    public final void translateTo(Tick event, long sequence){
        symbol = SYMBOLS.get(random.nextInt(SYMBOLS.size()));
        event.set(symbol, QuoteBoyType.FAKE.name(),UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                symbol.endsWith("USD") ? random.nextInt(10000) : random.nextDouble());
    }

}
