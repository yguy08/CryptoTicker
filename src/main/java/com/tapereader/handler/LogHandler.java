package com.tapereader.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.tapereader.model.Tick;

public class LogHandler implements EventHandler<Tick> {
    
    private static final Logger LOGGER = LogManager.getLogger("TickLogger");

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(event.toString());        
    }

}
