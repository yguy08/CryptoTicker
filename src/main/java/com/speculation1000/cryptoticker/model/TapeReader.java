package com.speculation1000.cryptoticker.model;

public interface TapeReader {
	
	void readTheTape();
	
	public void setBroker(Broker broker) throws Exception;
    
}
