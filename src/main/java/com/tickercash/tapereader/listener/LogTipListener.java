package com.tickercash.tapereader.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.framework.Listener;
import com.tickercash.tapereader.model.Tick;

public class LogTipListener implements Listener {
    
    private static final Logger LOGGER = LogManager.getLogger("LogTipListener");

    @Override
    public void onEvent(Tick tick) {
        // TODO Auto-generated method stub
        
    }

}
