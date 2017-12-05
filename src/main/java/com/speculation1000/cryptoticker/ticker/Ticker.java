package com.speculation1000.cryptoticker.ticker;

import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;

public interface Ticker {
   
    public void start() throws Exception;
    
    public void reset() throws Exception;
    
    void setTape(Tape tape);
    
    void subscribe(String s);
    
    void addEventHandler(EventHandler handler);

	void configure(String path);
    
}
