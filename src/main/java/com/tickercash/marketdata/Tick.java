package com.tickercash.marketdata;

import com.tickercash.util.NumberFormatPatterns;

public class Tick extends MarketDataEventVO {
    
    private double last;
    
    public Tick(String symbol, String feed, long timestamp, double last){
        super(symbol, feed, timestamp);
    	this.last = last;
    }
    
    public double getLast(){
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }
    
    @Override
    public String toString() {
        return super.toString()+" "+NumberFormatPatterns.SAT_FORMAT.format(getLast());
    }
}
