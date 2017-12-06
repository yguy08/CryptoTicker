package com.speculation1000.cryptoticker.ticker;

import java.io.FileInputStream;
import java.util.Properties;

import com.speculation1000.cryptoticker.core.TickerFunction;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;

public class SimpleTicker implements Ticker {
	
	private Tape tape;
	
	public Properties config;
	
	@Override
	public void start() throws Exception {
		tape.start();
	}

	@Override
	public void setTape(Tape tape) {
		this.tape = tape;
	}

	@Override
	public void subscribe(String s) {
	    tape.subscribe(s);	
	}

	@Override
	public void addEventHandler(EventHandler handler) {
	    tape.addEventHandler(handler);	
	}
	
    @Override
	public void reset() throws Exception {
		
	}

	@Override
	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        setTape(TickerFunction.TAPEFACTORY.apply(config.getProperty("tape")));
		tape.configure(path);
	}

}
