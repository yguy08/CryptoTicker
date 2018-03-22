package com.tickercash.event.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lmax.disruptor.EventHandler;
import com.tickercash.event.MarketDataEvent;

public class MarketEventLogger implements EventHandler<MarketDataEvent>{
    
    private static final Logger LOGGER = LogManager.getLogger("MarketEventLogger");

    @Override
    public void onEvent(MarketDataEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(event.get().toString());
    }

}
