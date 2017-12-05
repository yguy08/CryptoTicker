package com.speculation1000.cryptoticker.event.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.event.Tick;

public class Counter implements EventHandler {

	private static final Logger LOGGER = LogManager.getLogger("Counter");
	
	private static int tickCount = 0;
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		LOGGER.info(tickCount++);
	}

}
