package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.ticker.Ticker;

public interface TapeReader {
	
	void readTheTape() throws Exception;
	
	public void setTicker(Ticker ticker) throws Exception;
    
}
