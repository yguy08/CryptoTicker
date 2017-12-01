package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.Ticker;

public interface TapeReader {
	
	void setTicker(Ticker ticker);
	
	void setTape(Tape tape);
	
	void readTheTape() throws Exception;
	
	void configure(String cfg) throws Exception;
	
	void subscribe(String symbol);
    
}
