package com.speculation1000.cryptoticker.tape;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.speculation1000.cryptoticker.event.handler.EventHandler;

public class CsvTape extends Tape {
	
	private File path;
	
	@Override
	public void start() throws Exception {
		disruptor.start();
        while(true){
            onData(null);
        }
        //disruptor.shutdown();
	}

	@Override
	public void addEventHandler(EventHandler handler) {
		disruptor.handleEventsWith(handler::onTick);
	}

	@Override
	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        setFilePath(config.getProperty("csv.file"));	    	
	}
	
    private void setFilePath(String property) {
		
	}

	@Override
	public void subscribe(String symbol) {
		
	}

}
