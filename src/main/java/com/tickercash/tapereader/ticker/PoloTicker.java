package com.tickercash.tapereader.ticker;

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
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tickercash.tapereader.framework.EventHandler;
import com.tickercash.tapereader.framework.Ticker;
import com.tickercash.tapereader.model.Tick;
import com.tickercash.tapereader.util.DisruptorClerk;
import com.tickercash.tapereader.util.UniqueCurrentTimeMS;

public class PoloTicker implements Ticker {
    
    protected AtomicBoolean running = new AtomicBoolean(false);
    
    protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    private Exchange EXCHANGE;
    
    private PoloniexMarketDataServiceRaw marketDataService;
    
    protected final Disruptor<Tick> disruptor;
    
    protected final RingBuffer<Tick> ringBuffer;
    
    @SuppressWarnings("unchecked")
    @Inject
    public PoloTicker(EventHandler handler) {
        disruptor = DisruptorClerk.createDefaultMarketEventDisruptor();
        ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(handler::onEvent);
        disruptor.start();
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
        marketDataService = (PoloniexMarketDataServiceRaw) (PoloniexMarketDataService) EXCHANGE.getMarketDataService();
    }
    
    private final void translateTo(Tick event, long sequence, String symbol, PoloniexMarketData chartData) {
        event.set(symbol, TickerType.POLONIEX.toString(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(), 
                chartData.getLast().doubleValue(), chartData.getBaseVolume().intValue());
    }

    @Override
    public void start() throws Exception {
        running.set(true);
        while(running.get()) {
            marketDataService.getAllPoloniexTickers().entrySet().stream().forEach(s -> ringBuffer.publishEvent(this::translateTo, s.getKey(), s.getValue()));
            Thread.sleep(5000);
        }
    }

}
