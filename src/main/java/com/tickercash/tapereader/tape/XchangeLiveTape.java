package com.tickercash.tapereader.tape;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.tickercash.tapereader.core.Config;
import com.tickercash.tapereader.core.TickerFunction;
import com.tickercash.tapereader.core.UniqueCurrentTimeMS;


public class XchangeLiveTape extends Tape {

    private static final Logger LOGGER = LogManager.getLogger("XchangeLiveTape");
    
    public Exchange EXCHANGE = null;
    
    private Ticker ticker = null;
    
    private int sleep;
    
    @Override
    public void start() throws Exception {
        disruptor.start();
        while(true) {
            for(String symbol : symbols){
	            try{
	            	ticker = EXCHANGE.getMarketDataService().getTicker(new CurrencyPair(symbol));
	            	onTick(symbol,UniqueCurrentTimeMS.uniqueCurrentTimeMS(),ticker.getLast().doubleValue());
	                Thread.sleep(sleep);
            	}catch(Exception e){
            		LOGGER.error(e);
            		Thread.sleep(60000);
            	}
          }
	   }
    }

	@Override
	public void configure() throws Exception {
        
        setExchange(Config.getXchangeExchange());
        
        setSleep(Config.getThrottle());
        
	};
	
    private void setExchange(String exchange) {
		EXCHANGE = TickerFunction.XCHANGEFUNC.apply(exchange);
	}
    
    private void setSleep(int sleep) {
		this.sleep = sleep;
	}
	
}
