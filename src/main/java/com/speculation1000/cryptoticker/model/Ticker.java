package com.speculation1000.cryptoticker.model;

public interface Ticker {
   
    public void start() throws Exception;
    
    public void reset() throws Exception;
    
    public void setDataFeed(DataFeed dataFeed) throws Exception;
    
}
