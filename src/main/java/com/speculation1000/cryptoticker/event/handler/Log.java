package com.speculation1000.cryptoticker.event.handler;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.event.Tick;

public class Log implements EventHandler {

	private static final Logger LOGGER = LogManager.getLogger("TickEventHandler");
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		LOGGER.info(tick);		
	}

	@Override
	public void configure(Properties prop) {
		// TODO Auto-generated method stub
		
	}

}
