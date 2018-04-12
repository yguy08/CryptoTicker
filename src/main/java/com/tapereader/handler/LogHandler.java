package com.tapereader.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.tapereader.framework.Event;

public class LogHandler implements EventHandler<Event> {
    
    private static final Logger LOGGER = LogManager.getLogger("TickLogger");

    @Override
    public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(event.toString());        
    }

}
