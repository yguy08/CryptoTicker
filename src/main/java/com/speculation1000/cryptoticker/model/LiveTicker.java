package com.speculation1000.cryptoticker.model;

public class LiveTicker implements Ticker {
	
	private DataFeed dataFeed;
	
	@Override
	public void setDataFeed(DataFeed dataFeed){
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
