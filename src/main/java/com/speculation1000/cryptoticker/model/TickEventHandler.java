package com.speculation1000.cryptoticker.model;

import java.io.File;
import java.io.FileWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

public class TickEventHandler {
	
	private static final Logger logger = LogManager.getLogger("TickEventHandler");
	
	public static void logEvent(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		logger.info(tick);
	}
	
	public static void saveEvent(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		final FileWriter fw = new FileWriter(new File("btcusdt.csv"),true);
		
        SimpleResultSet rs = new SimpleResultSet();
        rs.addRow(tick.toString());
        new Csv().write(fw, rs);
		
		fw.close();

		System.out.println(tick);
	}

}
