package com.tapereader.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.tapereader.model.Tick;

public class CounterHandler implements EventHandler<Tick> {
    
    private static final Logger LOGGER = LogManager.getLogger("CounterHandler");
    
    private static int count;

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(++count);
    }
}
