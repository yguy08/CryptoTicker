package com.tickercash.tapereader.clerk.polo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataService;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;

import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.clerk.XQuoteBoy;
import com.tickercash.tapereader.marketdata.Tick;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;
import com.tickercash.tapereader.wire.Transmitter;

public class PoloQuoteBoy extends XQuoteBoy {
    
    private PoloniexMarketDataServiceRaw marketDataService;
    
    public PoloQuoteBoy(Transmitter transmitter) {
        super(transmitter, PoloniexExchange.class.getName());
        marketDataService = (PoloniexMarketDataServiceRaw) (PoloniexMarketDataService) EXCHANGE.getMarketDataService();
    }

	@Override
	public void getQuotes() throws Exception {
        disruptor.start();
        Runnable task = () -> {
            try {
                marketDataService.getAllPoloniexTickers().entrySet().stream().forEach(s 
                        -> ringBuffer.publishEvent(this::translateTo, s.getKey(), s.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executor.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);		
	}
    
    private final void translateTo(Tick event, long sequence, String symbol, PoloniexMarketData chartData) {
        event.set(symbol, QuoteBoyType.POLONIEX.toString(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                chartData.getLast().doubleValue(), chartData.getBaseVolume().intValue());
    }

}
