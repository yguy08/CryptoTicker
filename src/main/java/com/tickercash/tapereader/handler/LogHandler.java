package com.tickercash.tapereader.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.framework.Handler;
import com.tickercash.tapereader.model.Tick;

public class LogHandler implements Handler {
    
    private static final Logger LOGGER = LogManager.getLogger("TickLogger");

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(event.toString());
    }

}
