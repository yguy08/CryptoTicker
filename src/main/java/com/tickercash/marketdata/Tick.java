package com.tickercash.marketdata;

import com.tickercash.util.NumberFormatPatterns;
import com.tickercash.util.UniqueCurrentTimeMS;

public class Tick extends MarketEvent {
    
    private String symbol;
    private long timestamp;
    private double last;
    
    public Tick(){
    	// 
    }
    
    public Tick(String symbol, double last){
    	setSymbol(symbol);
    	setTimestamp(UniqueCurrentTimeMS.uniqueCurrentTimeMS());
    	setLast(last);
    }
    
    public Tick(String symbol, long timestamp, double last){
    	setSymbol(symbol);
    	setTimestamp(timestamp);
    	setLast(last);
    }
    
    public static final Tick set(String symbol, long time, double doubleValue) {
        return new Tick(symbol,time, doubleValue);
    }

    public String getSymbol(){
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public double getLast(){
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }
    
    @Override
    public String toString() {
        return this.symbol+","+this.timestamp+","+NumberFormatPatterns.SAT_FORMAT.format(this.last);
    }
}
