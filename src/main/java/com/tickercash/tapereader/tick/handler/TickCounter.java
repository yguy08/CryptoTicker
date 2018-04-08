package com.tickercash.tapereader.tick.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.tick.Tick;

public class TickCounter implements TickHandler {
    
    private static final Logger LOGGER = LogManager.getLogger("TickCounter");
    
    private static int count;

    @Override
    public void onTick(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(++count);
    }
}
