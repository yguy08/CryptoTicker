package com.speculation1000.cryptoticker.event.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.core.FileWriterPerTest;
import com.speculation1000.cryptoticker.event.Tick;

public class Save2File implements TickEventHandler {

	private static final Logger LOGGER = LogManager.getLogger("TickEventHandler");
	
	private static File file;
	
	private static FileWriter writer;
	static { 
		try {
			file = File.createTempFile("foo", ".txt");
			writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			file.delete();
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static int count = 0;
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		FileWriterPerTest.writeBuffered(tick.toString(),8192,file,writer);
		count++; //count handle
		LOGGER.info("ticks: "+count);
	}

}
