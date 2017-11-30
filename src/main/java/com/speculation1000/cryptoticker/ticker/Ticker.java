package com.speculation1000.cryptoticker.ticker;

import com.speculation1000.cryptoticker.tape.Tape;

public interface Ticker {
   
    public void start() throws Exception;
    
    public void reset() throws Exception;
    
    public void setTape(Tape tape) throws Exception;
    
}
