package com.tickercash.tapereader.model;

import com.tickercash.tapereader.framework.Event;

public class Tick implements Event {
    
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
    
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public void setLast(double last){
        this.last = last;
    }
    
    public void setVolume(int volume){
        this.volume = volume;
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
        return this.symbol+" "+this.feed+" "+this.timestamp+" "+this.last;
    }

    public void set(Tick event) {
        // TODO Auto-generated method stub
    }
}
