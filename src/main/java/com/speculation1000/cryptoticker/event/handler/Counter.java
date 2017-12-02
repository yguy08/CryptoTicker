package com.speculation1000.cryptoticker.event.handler;

import com.speculation1000.cryptoticker.event.Tick;

public class Counter implements EventHandler {

	private static final int tickCount = 0;
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		//LOG count++
	}

}
