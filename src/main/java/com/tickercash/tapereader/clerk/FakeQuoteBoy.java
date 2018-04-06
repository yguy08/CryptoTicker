package com.tickercash.tapereader.clerk;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;
import com.tickercash.tapereader.wire.Transmitter;

public class FakeQuoteBoy extends AbstractQuoteBoy {
    
    public FakeQuoteBoy(Transmitter transmitter) {
		super(transmitter);
	}

	private final List<String> SYMBOLS = Arrays.asList("BTC/USD","ETH/BTC","XRP/BTC","BCH/BTC","LTC/BTC");
    
    private final Random random = new Random();

    private String symbol;
    
	@Override
	public void getQuotes() throws Exception {
        disruptor.start();
        running.set(true);
        while(running.get()) {
            ringBuffer.publishEvent(this::translateTo);
            Thread.sleep(1000);
        }
	}
    
    public final void translateTo(Tick event, long sequence){
        symbol = SYMBOLS.get(random.nextInt(SYMBOLS.size()));
        event.set(symbol, QuoteBoyType.FAKE.name(),UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                symbol.endsWith("USD") ? random.nextInt(10000) : random.nextDouble());
    }

}
