package com.tickercash.tapereader.event.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.event.Tick;

public class Log extends TickEventHandler {

	private static final Logger LOGGER = LogManager.getLogger("TickEventHandler");

	@Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
		LOGGER.info(event);
		
	}

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
