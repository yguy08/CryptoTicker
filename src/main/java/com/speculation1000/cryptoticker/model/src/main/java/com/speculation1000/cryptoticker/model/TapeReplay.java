package com.speculation1000.cryptoticker.model;

public class TapeReplay implements Ticker {
    
    private Tape dataFeed;

	@Override
	public void start() throws Exception {
		dataFeed.start();		
	}

	@Override
	public void setDataFeed(Tape dataFeed) throws Exception {
        this.dataFeed = dataFeed;        
	}

	@Override
	public void reset() throws Exception {
				
	}

}
