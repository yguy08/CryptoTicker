package com.speculation1000.cryptoticker.tape;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.handler.EventHandler;

import net.openhft.chronicle.bytes.Bytes;

public class FakeTape extends Tape {
	
	private Bytes<?> bytes = Bytes.elasticByteBuffer();
	
	@Override
	public void start() throws Exception {
		disruptor.start();
        while(true){
        	onTick(bytes
					.append("BTC/USDT").append(' ')
					.append(UniqueCurrentTimeMS.uniqueCurrentTimeMS()).append(' ')
					.append(10000.00).append(' ')
					.append(10000.00).append(' ')
					.append(10000.00).append(' ')
					.append(500000.00).append(' '));
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
