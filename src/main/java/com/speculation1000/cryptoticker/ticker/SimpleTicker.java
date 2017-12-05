package com.speculation1000.cryptoticker.ticker;

import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;

public class SimpleTicker implements Ticker {
	
	private Tape tape;
	
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
	public void configure(String path) {
		try {
			tape.configure(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
