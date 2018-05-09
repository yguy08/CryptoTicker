package com.tapereader.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.openhft.chronicle.wire.AbstractMarshallable;

@Entity
@Table( name = "TICKS" )
public class Tick extends AbstractMarshallable {
    
    private long id;
    
    private String symbol;
    private String feed;
    private long timestamp;
    private double last;
    private int volume;
    
    public Tick(){
        
    }
    
    public Tick(String symbol, String feed, long timestamp, double last, int volume){
        this.symbol = symbol;
        this.feed = feed;
        this.timestamp = timestamp;
        this.last = last;
        this.volume = volume;
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
    
    @Id
    @GeneratedValue( generator="increment" )
    @GenericGenerator( name="increment", strategy = "increment" )
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
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
}
