package com.tickercash.tapereader.event.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.core.UniqueCurrentTimeMS;
import com.tickercash.tapereader.event.Tick;

public class Save2File extends TickEventHandler {

	private static final Logger LOGGER = LogManager.getLogger("Save2File");
	
	private static String file; 
	
	private static BufferedWriter buffWriter;

	@Override
	public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
		buffWriter.append('\n');
		buffWriter.append(event.toString());		
		buffWriter.flush();
		
	}

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
