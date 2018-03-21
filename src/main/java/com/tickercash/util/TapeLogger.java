package com.tickercash.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TapeLogger {

    private static final Logger LOGGER = LogManager.getLogger("TapeLogger");
    
    public static final Logger getLogger(){
    	return LOGGER;
    }

}
