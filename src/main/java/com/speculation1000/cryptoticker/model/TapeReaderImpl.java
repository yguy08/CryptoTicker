package com.speculation1000.cryptoticker.model;

import java.io.FileInputStream;
import java.util.Properties;

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
	public void setTicker(Ticker ticker) {
        this.ticker = ticker;		
	}

	@Override
	public void setTape(Tape tape) {
		ticker.setTape(tape);
	}
	
	@Override
	public void subscribe(String symbol) {
		ticker.getTape().subscribe(symbol);
	}

	@Override
	public void configure(String cfg) throws Exception {
		config = new Properties();
        config.load(new FileInputStream(cfg));
		
        ticker = Ticker.TICKERFACTORY.apply(config.getProperty("ticker"));
        
        tape = Tape.TAPEFACTORY.apply(config.getProperty("tape"));
        ticker.setTape(tape);
        
        tape.addTickEventHandler(Tape.TICKEVENTFACTORY.apply(config.getProperty("tickevent")));
        
        tape.setExchange(Tape.EXCHANGEFACTORY.apply(config.getProperty("exchange")));
        
        tape.subscribe(config.getProperty("symbols"));
        
     }
	
	

}
