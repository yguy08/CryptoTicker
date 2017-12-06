package com.speculation1000.cryptoticker.tape;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.Tick;
import com.speculation1000.cryptoticker.event.handler.EventHandler;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.core.util.ObjectUtils;

public class FakeTape extends Tape {
	
	@Override
	public void start() throws Exception {
		disruptor.start();
        while(true){
        	onTick(ObjectUtils.convertTo(Bytes.class, 
        			new Tick().set("BTC/USDT",UniqueCurrentTimeMS.uniqueCurrentTimeMS(),
        					10000.00,10000.00,10000.00,100000)));
            //Thread.sleep(500, 1);
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
		
	}

}
