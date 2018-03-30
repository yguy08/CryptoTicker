package com.tickercash.tapereader.event.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.tickercash.tapereader.marketdata.Tick;

public class TickCounter implements EventHandler<Tick> {
    
    private static final Logger LOGGER = LogManager.getLogger("TickCounter");
    
    private static int count;

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(++count);
    }
}
