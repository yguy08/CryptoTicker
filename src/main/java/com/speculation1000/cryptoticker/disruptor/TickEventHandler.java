package com.speculation1000.cryptoticker.disruptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.speculation1000.cryptoticker.marketdataevent.Tick;

public class TickEventHandler implements EventHandler<Tick>{
	private static final Logger logger = LogManager.getLogger("TickEventHandler");
    @Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch){
    	logger.info(event);
    }
}
