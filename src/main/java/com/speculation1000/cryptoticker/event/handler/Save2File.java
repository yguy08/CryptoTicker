package com.speculation1000.cryptoticker.event.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.core.UniqueCurrentTimeMS;
import com.speculation1000.cryptoticker.event.Tick;

public class Save2File implements EventHandler {

	private static final Logger LOGGER = LogManager.getLogger("Save2File");
	
	private static String file = "ticks-"+UniqueCurrentTimeMS.uniqueCurrentTimeMS()+".csv";
	
	private static BufferedWriter buffWriter;
	
	static {
		try {
			buffWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			LOGGER.error(e);
		};
	}
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		buffWriter.append('\n');
		buffWriter.append(tick.toString());
		
		buffWriter.flush();
	}
	
}
