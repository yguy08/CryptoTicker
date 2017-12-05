package com.speculation1000.cryptoticker.tape;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.function.TickerFunction;
import com.speculation1000.cryptoticker.tapereader.TapeReader;

public class XchangeLiveTape extends Tape {

    private static final Logger LOGGER = LogManager.getLogger("XchangeDataFeed");
    
    public Exchange EXCHANGE = null;
    
    @Override
    public void start() throws Exception {
        disruptor.start();
        while(true) {
            for(String symbol : symbols){
                onData(TapeReader.TICK_FACTORY
                .apply(symbol, EXCHANGE
                .getMarketDataService()
                .getTicker(new CurrencyPair(symbol))));
                Thread.sleep(1000);
          }
	   }
    }

	@Override
	public void subscribe(String symbol) {
		symbols.add(symbol);
	}

	@Override
	public void addEventHandler(EventHandler handler) {
		disruptor.handleEventsWith(handler::onTick);
	}

	@Override
	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        setExchange(config.getProperty("xchange.exchange"));
        
        //if save to file, etc...
        
	};
	
    private void setExchange(String exchange) {
		EXCHANGE = TickerFunction.EXCHANGEFACTORY.apply(exchange);
	}
	
}
