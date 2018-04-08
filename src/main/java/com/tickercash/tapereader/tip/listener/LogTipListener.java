package com.tickercash.tapereader.tip.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.espertech.esper.client.EventBean;

public class LogTipListener implements TipListener {
    
    private static final Logger LOGGER = LogManager.getLogger("LogTipListener");
    
    @Override
    public void onTip(EventBean[] newTick, EventBean[] oldTick) {
        LOGGER.info((String) newTick[0].get("symbol"));        
    }

}
