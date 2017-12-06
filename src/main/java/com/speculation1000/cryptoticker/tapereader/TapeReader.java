package com.speculation1000.cryptoticker.tapereader;

import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.Ticker;

public interface TapeReader {

	TapeReader setTicker(Ticker ticker);
    
    TapeReader setTape(Tape tape);
    
    TapeReader subscribe(String symbol);
    
    TapeReader addEvent(EventHandler handler);
    
    TapeReader configure(String path) throws Exception;

    void readTheTape() throws Exception;
}
