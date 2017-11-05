package com.speculation1000.cryptoticker.core;

public class GenericTickImpl implements ITick {
	
    private String tickerSymbol;
    
    private long timestamp;
    
    private double last;
    
    private String exchange;
    
    public GenericTickImpl(String tickerSymbol, long timestamp, double last,String exchange) {
        this.tickerSymbol = tickerSymbol;
        this.timestamp = timestamp;
        this.last = last;
        this.exchange = exchange;
    }
    
    @Override 
    public String toString() {
    	return this.tickerSymbol + " " + this.timestamp + " " + this.last + " " + this.exchange;
    }    

}
