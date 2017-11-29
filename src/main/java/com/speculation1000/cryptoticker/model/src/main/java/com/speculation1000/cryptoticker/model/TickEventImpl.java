package com.speculation1000.cryptoticker.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.dto.marketdata.Ticker;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;

public class TickEventImpl implements TickEvent {

	private static final Logger LOGGER = LogManager.getLogger("TickEventHandler");
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		LOGGER.info(tick);		
	}

	@Override
	public void translate(Tick event, long sequence, Ticker ticker) {
        event.set(ticker.getCurrencyPair().toString(),UniqueCurrentTimeMS.uniqueCurrentTimeMS(),
        		ticker.getLast().doubleValue(),ticker.getBid().doubleValue(),ticker.getAsk().doubleValue(),
        		ticker.getVolume().intValue());		
	}

}
