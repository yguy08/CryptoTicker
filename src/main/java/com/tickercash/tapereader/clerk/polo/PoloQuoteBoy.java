package com.tickercash.tapereader.clerk.polo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataService;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;

import com.tickercash.tapereader.clerk.QuoteBoyType;
import com.tickercash.tapereader.clerk.XQuoteBoy;

public class PoloQuoteBoy extends XQuoteBoy {
    
    private PoloniexMarketDataServiceRaw marketDataService;
    
    
    public PoloQuoteBoy() {
        super(PoloniexExchange.class.getName());
        marketDataService = (PoloniexMarketDataServiceRaw) (PoloniexMarketDataService) EXCHANGE.getMarketDataService();
    }

    @Override
    public String getName() {
        return QuoteBoyType.POLONIEX.toString();
    }

    @Override
    public void start() throws Exception {
        disruptor.start();
        Runnable task = () -> {
            try {
                marketDataService.getAllPoloniexTickers().entrySet().stream().forEach(s 
                        -> ringBuffer.publishEvent(PoloTranslator::translateTo, s.getKey(), s.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executor.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);
    }

}
