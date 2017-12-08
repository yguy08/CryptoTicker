package com.speculation1000.cryptoticker.ticker;

import java.util.Properties;

import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;

public interface Ticker {
   
    public void start() throws Exception;
    
    public void reset() throws Exception;
    
    void setTape(Tape tape);
    
    void subscribe(String s);
    
    void addEventHandler(EventHandler handler);

	void configure(Properties path) throws Exception;
    
}
