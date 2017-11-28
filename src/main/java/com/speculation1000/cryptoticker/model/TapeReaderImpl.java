package com.speculation1000.cryptoticker.model;

public class TapeReaderImpl implements TapeReader {

	private Ticker ticker;

	@Override
	public void readTheTape() throws Exception {
		ticker.start();
	}

	@Override
	public void setTicker(Ticker ticker) throws Exception {
        this.ticker = ticker;		
	}

}
