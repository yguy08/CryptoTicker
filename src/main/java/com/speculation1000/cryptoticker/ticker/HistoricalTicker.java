package com.speculation1000.cryptoticker.ticker;

import com.speculation1000.cryptoticker.tape.Tape;

public class HistoricalTicker implements Ticker {
    
    private Tape tape;

	@Override
	public void start() throws Exception {
		tape.start();		
	}

	@Override
	public void setTape(Tape tape) {
        this.tape = tape;
	}

	@Override
	public void reset() throws Exception {
				
	}

	@Override
	public Tape getTape() {
		return tape;
	}

}
