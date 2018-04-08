package com.tickercash.tapereader.tick.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.tickercash.tapereader.tick.Tick;

public class LogTipHandler implements TickHandler {
    
    private static final Logger LOGGER = LogManager.getLogger("TickLogger");

    @Override
    public void onTick(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(event.toString());
    }

}
