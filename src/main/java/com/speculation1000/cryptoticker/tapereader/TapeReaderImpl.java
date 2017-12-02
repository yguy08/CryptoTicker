package com.speculation1000.cryptoticker.tapereader;

import java.io.FileInputStream;
import java.util.Properties;

import org.knowm.xchange.Exchange;

import com.speculation1000.cryptoticker.event.handler.TickEventHandler;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.Ticker;

public class TapeReaderImpl implements TapeReader {

    private Ticker ticker;
    
    private Tape tape;
    
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
	public void configure(String cfg) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(cfg));
     }

	@Override
	public TapeReader setTape(Tape t) {
		ticker.setTape(t);
		return this;
	}

	@Override
	public TapeReader subscribe(String symbol) {
		ticker.getTape().subscribe(symbol);
		return this;
	}

	@Override
	public TapeReader addTickEvent(TickEventHandler handler) {
        ticker.getTape().addTickEventHandler(handler);
		return this;
	}

	@Override
	public TapeReader setExchange(Exchange ex) {
		ticker.getTape().setExchange(ex);
		return this;
	}
	
	

}
