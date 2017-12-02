package com.speculation1000.cryptoticker.tape;

import org.knowm.xchange.Exchange;

import com.speculation1000.cryptoticker.event.handler.EventHandler;

public class CsvTape extends Tape {
	
	private static final String record = "BTC/USDT,123405968,10000.00,10000.01,10000.0233333,10000.03333333,10000.0333333";
	
	//file...
	
	@Override
	public void start() throws Exception {
		disruptor.start();
        while(true){
            onData(record);
        }
        //disruptor.shutdown();
	}

	@Override
	public Tape subscribe(String symbol) {
		return this;		
	}

	@Override
	public Tape addTickEventHandler(EventHandler handler) {
		disruptor.handleEventsWith(handler::onTick);
		return this;
		
	}

	@Override
	public void setExchange(Exchange exchange) {
	
	}

}
