package com.tickercash.tapereader.event.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tickercash.tapereader.core.UniqueCurrentTimeMS;
import com.tickercash.tapereader.event.Tick;

public class Save2File implements EventHandler {

	private static final Logger LOGGER = LogManager.getLogger("Save2File");
	
	private static String file; 
	
	private static BufferedWriter buffWriter;
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		buffWriter.append('\n');
		buffWriter.append(tick.toString());		
		buffWriter.flush();
	}

	@Override
	public void configure(Properties prop) {
		String dir = prop.getProperty("file.directory");
	    String name = prop.getProperty("file.name");
        file = dir+"/"+name+UniqueCurrentTimeMS.uniqueCurrentTimeMS()+".csv";
		
		try {
			buffWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			LOGGER.error(e);
		};
	}
	
}
