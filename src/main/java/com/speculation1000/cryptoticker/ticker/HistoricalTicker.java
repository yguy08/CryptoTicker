package com.speculation1000.cryptoticker.ticker;

import com.speculation1000.cryptoticker.tape.Tape;

public class HistoricalTicker implements Ticker {
    
    private Tape dataFeed;

	@Override
	public void start() throws Exception {
		dataFeed.start();		
	}

	@Override
	public void setTape(Tape dataFeed) throws Exception {
        this.dataFeed = dataFeed;        
	}

	@Override
	public void reset() throws Exception {
				
	}

}
