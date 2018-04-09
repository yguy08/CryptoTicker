package com.tickercash.tapereader.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.framework.Handler;
import com.tickercash.tapereader.model.Tick;

public class CounterHandler implements Handler {
    
    private static final Logger LOGGER = LogManager.getLogger("CounterHandler");
    
    private static int count;

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(++count);
    }
}
