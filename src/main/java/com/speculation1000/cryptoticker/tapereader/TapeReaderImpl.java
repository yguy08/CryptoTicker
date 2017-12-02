package com.speculation1000.cryptoticker.tapereader;

import org.knowm.xchange.Exchange;

import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.Ticker;

public class TapeReaderImpl implements TapeReader {

    private Ticker ticker;

	@Override
	public void readTheTape() throws Exception {
        ticker.start();
	}

    @Override
    public TapeReader setTicker(Ticker t) {
        ticker = t;
        return this;
	}

	@Override
	public TapeReader setTape(Tape t) {
		ticker.setTape(t);
		return this;
	}

	@Override
	public TapeReader subscribe(String symbol) {
		ticker.getTape().subscribe(symbol);
		return this;
	}

	@Override
	public TapeReader addTickEvent(EventHandler handler) {
        ticker.getTape().addTickEventHandler(handler);
		return this;
	}

	@Override
	public TapeReader setExchange(Exchange ex) {
		ticker.getTape().setExchange(ex);
		return this;
	}	

}
