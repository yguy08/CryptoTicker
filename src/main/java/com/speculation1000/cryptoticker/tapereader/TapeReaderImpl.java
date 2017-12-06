package com.speculation1000.cryptoticker.tapereader;

import java.io.FileInputStream;
import java.util.Properties;

import com.speculation1000.cryptoticker.core.TickerFunction;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.Ticker;

public class TapeReaderImpl implements TapeReader {

    private Ticker ticker;
    
    private Properties config;

	@Override
	public void readTheTape() throws Exception {
        ticker.start();
	}

    @Override
    public TapeReader setTicker(Ticker t) {
        ticker = t;
        return this;
	}

	@Override
	public TapeReader setTape(Tape t) {
		ticker.setTape(t);
		return this;
	}

	@Override
	public TapeReader subscribe(String symbol) {
		ticker.subscribe(symbol);
		return this;
	}

	@Override
	public TapeReader addEvent(EventHandler handler) {
        ticker.addEventHandler(handler);
		return this;
	}

	@Override
	public TapeReader configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        setTicker(TickerFunction.TICKERFUNC.apply(config.getProperty("ticker")));
        ticker.setTape(TickerFunction.TAPEFACTORY.apply(config.getProperty("tape")));
		ticker.configure(path);
		return this;
	}	

}
