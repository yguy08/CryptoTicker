package com.tickercash.clerk;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.tickercash.event.translator.MarketDataTranslator;

public class XchangeTicker extends QuoteBoy {
        
    private Exchange EXCHANGE = null;
    
    private Ticker ticker = null;
    
    private CurrencyPair pair;
    
    private int throttle = 1000;
    
    private static final Logger LOGGER = LogManager.getLogger("XchangeTicker");
        
    public XchangeTicker(String exchangeName) {
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }

    @Override
    public void start() throws Exception {
    	disruptor.start();        
        while(true) {
            for(String s : subscriptions) {
                pair = new CurrencyPair(s);
                try {
                    ticker = EXCHANGE.getMarketDataService().getTicker(pair);
                    ringBuffer.publishEvent(MarketDataTranslator::translateTo, EXCHANGE.getExchangeSpecification().getExchangeName(), ticker);
                    Thread.sleep(throttle);
                }catch(Exception e) {
                	LOGGER.error(e);
    				Thread.sleep(throttle);
                }
            }
       }        
    }
    
    public void setThrottle(int throttle){
        this.throttle = throttle; 
    }

}
