package com.speculation1000.cryptoticker.ticker;

import java.util.function.Function;

import com.speculation1000.cryptoticker.tape.Tape;

public interface Ticker {
   
    public void start() throws Exception;
    
    public void reset() throws Exception;
    
    void setTape(Tape tape);
    
    Tape getTape();
    
    public static final Function<String,Ticker> TICKERFACTORY = 
    		new Function<String,Ticker>(){

				@Override
				public Ticker apply(String t) {
					switch(t){
					case "live":
						return new LiveTicker();
					case "HistoricalTicker":
						return new HistoricalTicker();
					default:
						return new HistoricalTicker();
					}
				}
    	
    };
    
}
