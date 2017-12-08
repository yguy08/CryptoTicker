package com.speculation1000.cryptoticker.tapereader;

import java.io.FileInputStream;
import java.util.Properties;

import com.speculation1000.cryptoticker.core.TickerFunction;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.Ticker;

public class TapeReader {

    private Ticker ticker;
    
    private Properties config;

	public void readTheTape() throws Exception {
        ticker.start();
	}

    public void setTicker(Ticker t) {
        ticker = t;
	}

	public void setTape(Tape t) {
		ticker.setTape(t);
	}

	public void subscribe(String symbol) {
		ticker.subscribe(symbol);
	}

	public void addEventHandler(EventHandler handler) {
        ticker.addEventHandler(handler);
	}

	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        setTicker(TickerFunction.TICKERFUNC.apply(config.getProperty("ticker")));
        setTape(TickerFunction.TAPEFACTORY.apply(config.getProperty("tape")));
        
        //set events
        String[] s = config.getProperty("event.handler").split(",");
        for(int i = 0; i < s.length;i++){
            addEventHandler(TickerFunction.EVENTFACTORY.apply(s[i]));
        }
		
        ticker.configure(config);
	}	

}
