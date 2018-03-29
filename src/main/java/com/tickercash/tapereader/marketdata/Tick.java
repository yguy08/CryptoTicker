package com.tickercash.tapereader.marketdata;

import com.tickercash.tapereader.util.NumberFormatPatterns;

public class Tick {
    
    private String symbol;
    private String feed;
    private long timestamp;
    private double last;
    private int volume;
    
    public Tick(){
        // For Disruptor Factory
    }
    
    public Tick(String symbol, String feed, long timestamp, double last){
        this.symbol = symbol;
        this.feed = feed;
        this.timestamp = timestamp;
        this.last = last;
    }
    
    public Tick(String symbol, String feed, long timestamp, double last, int volume){
        this.symbol = symbol;
        this.feed = feed;
        this.timestamp = timestamp;
        this.last = last;
        this.volume = volume;
    }
    
    public Tick set(String symbol, String feed, long timestamp, double last){
        this.symbol = symbol;
        this.feed = feed;
        this.timestamp = timestamp;
        this.last = last;
        return this;
    }
    
    public Tick set(String symbol, String feed, long timestamp, double last, int volume){
        this.symbol = symbol;
        this.feed = feed;
        this.timestamp = timestamp;
        this.last = last;
        this.volume = volume;
        return this;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getSymbol() {
        return symbol;
    }
    
    public String getFeed() {
        return feed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getLast(){
        return last;
    }
    
    public int getVolume() {
        return volume;
    }
    
    @Override
    public String toString() {
        return this.symbol+" "+this.feed+" "+this.timestamp+" "+NumberFormatPatterns.SAT_FORMAT.format(this.last);
    }
}
