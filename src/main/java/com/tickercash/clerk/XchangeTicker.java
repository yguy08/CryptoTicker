package com.tickercash.clerk;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.tickercash.event.translator.MarketDataTranslator;
import com.tickercash.util.TapeLogger;


public class XchangeTicker extends LiveDataClerk {
        
    private Exchange EXCHANGE = null;
    
    private Ticker ticker = null;
    
    private CurrencyPair pair;
    
    private int throttle = 1000;
        
    public XchangeTicker(String exchangeName) {
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }

    @Override
    public void start() {
        try{
        	
        }catch(Exception e){
        	
        }
    	disruptor.start();        
        while(true) {
            for(String s : subscriptions) {
                pair = new CurrencyPair(s);
                try {
                    ticker = EXCHANGE.getMarketDataService().getTicker(pair);
                    ringBuffer.publishEvent(MarketDataTranslator::translateTo, EXCHANGE.getExchangeSpecification().getExchangeName(), ticker);
                    Thread.sleep(throttle);
                }catch(Exception e) {
                    TapeLogger.getLogger().error(e);
                }
            }
       }        
    }
    
    public void setThrottle(int throttle){
        this.throttle = throttle; 
    }

}
