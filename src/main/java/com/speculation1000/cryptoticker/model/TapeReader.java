package com.speculation1000.cryptoticker.model;

public interface TapeReader {
	
	void readTheTape() throws Exception;
	
	public void setTicker(Ticker broker) throws Exception;
    
}
