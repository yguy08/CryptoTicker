package com.tickercash.tapereader.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.tapereader.TapeReader;

public class TapeSaver {

	private static final Logger LOGGER = LogManager.getLogger("TapeSaver");
	
	public static void main(String[] args) {
        TapeReader tapeReader = new TapeReader();
        
        try{
            tapeReader.configure("application.properties");
            tapeReader.readTheTape();
        }catch(Exception e){
        	LOGGER.error("ERROR: {}", e);
        }
    }

}
