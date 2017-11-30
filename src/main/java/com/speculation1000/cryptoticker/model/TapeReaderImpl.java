package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.ticker.Ticker;

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
