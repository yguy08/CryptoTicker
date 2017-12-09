package com.tickercash.tapereader.event.handler;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.event.Tick;

public class Counter implements EventHandler {

	private static final Logger LOGGER = LogManager.getLogger("Counter");
	
	private static int tickCount = 0;
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		LOGGER.info(tickCount++);
	}

	@Override
	public void configure(Properties prop) {
		
	}

}
