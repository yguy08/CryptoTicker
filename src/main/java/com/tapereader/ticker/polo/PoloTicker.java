package com.tapereader.ticker.polo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataService;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;

import com.google.inject.Inject;
import com.tapereader.framework.Tape;
import com.tapereader.model.Tick;
import com.tapereader.ticker.AbstractTicker;
import com.tapereader.ticker.TickerType;
import com.tapereader.util.UniqueCurrentTimeMS;

public class PoloTicker extends AbstractTicker {
    
    protected AtomicBoolean running = new AtomicBoolean(false);
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    private Exchange EXCHANGE;
    
    private PoloniexMarketDataServiceRaw marketDataService;
    
    @Inject
    public PoloTicker(Tape tape) {
        super(tape);
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
        marketDataService = (PoloniexMarketDataServiceRaw) (PoloniexMarketDataService) EXCHANGE.getMarketDataService();
    }
    
    private final void translateTo(Tick event, long sequence, String symbol, PoloniexMarketData chartData) {
        event.set(symbol, TickerType.POLONIEX.toString(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                chartData.getLast().doubleValue(), chartData.getBaseVolume().intValue());
    }

    @Override
    public void start() throws Exception {
        disruptor.start();
        running.set(true);
        while(running.get()) {
            marketDataService.getAllPoloniexTickers().entrySet().stream().forEach(s -> ringBuffer.publishEvent(this::translateTo, s.getKey(), s.getValue()));
            Thread.sleep(5000);
        }
    }

}
