package com.tickercash.tapereader.tip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.marketdata.Tick;

public class TipConfirmedListener implements TipListener {
    
    private static final Logger LOGGER = LogManager.getLogger("MarketEventLogger");

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info(event.toString());
    }

}
