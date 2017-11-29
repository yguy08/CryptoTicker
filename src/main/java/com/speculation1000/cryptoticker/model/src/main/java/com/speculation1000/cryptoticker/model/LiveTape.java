package com.speculation1000.cryptoticker.model;

public class LiveTape implements Ticker {
	
	private Tape dataFeed;
	
	@Override
	public void setDataFeed(Tape dataFeed){
		this.dataFeed = dataFeed;
	}
	
	@Override
	public void start() throws Exception {
		dataFeed.start();
	}
	
	@Override
	public void reset() throws Exception {
		
	}

}
