package com.tickercash.tapereader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.tickercash.model.UniqueCurrentTimeMS;

public class XchangeTicker extends com.tickercash.tapereader.Ticker {
    
    private final Logger LOGGER = LogManager.getLogger("XchangeTicker");
    
    private Exchange EXCHANGE = null;
    
    private Ticker ticker = null;
    
    private CurrencyPair pair;
    
    private int throttle = 1000;
    
    public XchangeTicker(String exchangeName) {
        super();
        EXCHANGE = ExchangeFactory.INSTANCE.createExchange(exchangeName);
    }

    @Override
    public void start() {
        disruptor.start();        
        while(true) {
            for(String s : subscriptions) {
                pair = new CurrencyPair(s);
                try {
                    ticker = EXCHANGE.getMarketDataService().getTicker(pair);
                    onTick(pair.toString(), UniqueCurrentTimeMS.uniqueCurrentTimeMS(),ticker.getLast().doubleValue());
                    Thread.sleep(throttle);
                }catch(Exception e) {
                    LOGGER.error(e);
                }
            }
       }        
    }
    
    public void setThrottle(int throttle){
        this.throttle = throttle; 
    }

}
