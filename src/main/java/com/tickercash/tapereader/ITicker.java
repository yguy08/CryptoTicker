package com.tickercash.tapereader;

/**
 * Ticker
 */
public interface ITicker {
    
    public void subscribe(String symbol);

    public void unsubscribe(String symbol);
    
    public void start();
    
    public void addTape(ITip tape);
    
}
