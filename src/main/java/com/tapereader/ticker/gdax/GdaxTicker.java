package com.tapereader.ticker.gdax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.service.GDAXMarketDataService;

import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;
import com.tapereader.framework.Transmitter;
import com.tapereader.model.Tick;
import com.tapereader.ticker.TickerType;
import com.tapereader.ticker.XTicker;
import com.tapereader.util.UniqueCurrentTimeMS;

public class GdaxTicker extends XTicker {
    
    private final GDAXMarketDataService MARKET_DATA_SERVICE;
    
    private static final Logger LOGGER = LogManager.getLogger("CMCQuoteBoy");
    
    private static final List<CurrencyPair> PAIRS = new ArrayList<>();
    
    @Inject
    public GdaxTicker(Transmitter transmitter) throws IOException {
        super(transmitter, GDAXExchange.class.getName());
        MARKET_DATA_SERVICE = (GDAXMarketDataService) EXCHANGE.getMarketDataService();
        
        for(GDAXProduct pair : MARKET_DATA_SERVICE.getGDAXProducts()) {
            if(pair.getTargetCurrency().equals(Currency.USD.toString()))
                PAIRS.add(new CurrencyPair(pair.getBaseCurrency(), pair.getTargetCurrency()));
        }
    }

    @Override
    public void start() throws Exception {
        disruptor.start();
        running.set(true);
        while(running.get()) {
            for(CurrencyPair pair : PAIRS) {
                try {
                    ringBuffer.publishEvent(this::translateTo, MARKET_DATA_SERVICE.getTicker(pair));
                    Thread.sleep(1100);
                } catch (Exception e) {
                    LOGGER.error(e);
                    Thread.sleep(10000);
                }
            }
        }
    }
    
    private final void translateTo(Tick event, long sequence, Ticker ticker) {
        event.set(ticker.getCurrencyPair().toString(), TickerType.GDAX.toString(), 
                UniqueCurrentTimeMS.uniqueCurrentTimeMS(), ticker.getLast().doubleValue(), ticker.getVolume().intValue());
    }

    @Override
    public void handleEventsWith(Disruptor disruptor) {
        disruptor.handleEventsWith(transmitter::transmit);
    }

}
