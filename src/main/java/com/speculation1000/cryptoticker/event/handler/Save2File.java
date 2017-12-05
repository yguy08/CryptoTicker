package com.speculation1000.cryptoticker.event.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.speculation1000.cryptoticker.event.Tick;

public class Save2File implements EventHandler {

	private static final Logger LOGGER = LogManager.getLogger("Save2File");
	
	private static File file;
	
	private static FileWriter writer;
	
	private static BufferedWriter buffWriter;
	
	public Save2File() {
		try {
			file = File.createTempFile("btc", ".txt");
			writer = new FileWriter(file);
			buffWriter = new BufferedWriter(writer,8192);
		}catch (Exception e) {
			LOGGER.error(e);
		} 
	}
	
	@Override
	public void onTick(Tick tick, long sequence, boolean endOfBatch) throws Exception {
		WRITER.accept(tick.toString());
	}
	
    private static final Consumer<String> WRITER = 
            new Consumer<String>() {   
    	    
    	public void accept(String t) {
    	        try {
    	            buffWriter.write(t);
    	            writer.flush();
    	        } catch (IOException e) {
					e.printStackTrace();
				} finally {
    	            // comment this out if you want to inspect the files afterward
    	            file.delete();
    	        }
            }
    	    
    };
	
}
