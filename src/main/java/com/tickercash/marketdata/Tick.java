package com.tickercash.marketdata;

public class Tick {
    
    private String symbol;
    private long timestamp;
    private double last;
    
    public Tick(){}
    
    public Tick(String symbol, long timestamp, double last){
    	setSymbol(symbol);
    	setTimestamp(timestamp);
    	setLast(last);
    }
    
    public Tick set(String string, long time, double doubleValue) {
        setSymbol(string);
        setTimestamp(time);
        setLast(doubleValue);
        return this;
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
        return this.symbol+","+this.timestamp+","+this.last;
    }
}
