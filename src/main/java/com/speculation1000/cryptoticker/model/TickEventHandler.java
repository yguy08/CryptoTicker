package com.speculation1000.cryptoticker.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TickEventHandler {
	
	private static final Logger logger = LogManager.getLogger("TickEventHandler");
	
	public static void logEvent(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		logger.info(tick);
	}
	
	public static void saveEvent(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		
	}

}
