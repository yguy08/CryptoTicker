package com.speculation1000.cryptoticker.tape;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.EventHandler;

public class FakeTape extends Tape {
	
	private static Tick fakeTick;
	
	@Override
	public void start() throws Exception {
		disruptor.start();
        while(true){
        	onData(fakeTick);
            //Thread.sleep(1000, 1);
        }
	}

	@Override
	public void addEventHandler(EventHandler handler) {
		disruptor.handleEventsWith(handler::onTick);
	}
	
    @Override
	public void subscribe(String symbol) {
		
	}

	@Override
	public void configure(String path) throws Exception {
		fakeTick = new Tick().set("BTC/USDT",UniqueCurrentTimeMS.uniqueCurrentTimeMS(),
				10000.00,10000.00,10000.00,100000);
		
		
	}

}
