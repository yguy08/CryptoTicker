package com.tickercash.marketdata;

import java.time.Instant;

public abstract class MarketDataEventVO {
    
    private String symbol;
    private String feed;
    private long unixTimeMS;
    
    public MarketDataEventVO(String symbol, String feed, long unixTimeMS){
        this.symbol = symbol;
        this.feed = feed;
        this.unixTimeMS = unixTimeMS;
    }
    
    public String getSymbol(){
        return this.symbol;
    }
    
    public String getFeed(){
        return this.feed;
    }
    
    public long getUnixTimeMs(){
        return this.unixTimeMS;
    }
    
    @Override
    public String toString(){
        return Instant.ofEpochMilli(this.unixTimeMS/1000).toString()+" "+this.symbol+":"+this.feed;
    }

}