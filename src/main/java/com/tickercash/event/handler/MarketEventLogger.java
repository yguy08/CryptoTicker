package com.tickercash.event.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.tickercash.marketdata.MarketEvent;

public class MarketEventLogger implements EventHandler<MarketEvent> {
	
    private final Logger LOGGER = LogManager.getLogger("MarketEventLogger");

	@Override
	public void onEvent(MarketEvent event, long sequence, boolean endOfBatch) throws Exception {
		LOGGER.info(event.toString());
	}

}
