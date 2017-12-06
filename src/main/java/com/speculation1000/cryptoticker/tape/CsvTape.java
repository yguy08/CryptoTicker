package com.speculation1000.cryptoticker.tape;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import com.speculation1000.cryptoticker.event.handler.EventHandler;

public class CsvTape extends Tape {
	
	private String path;
	
	@Override
	public void start() throws Exception {
		disruptor.start();
		try (Stream<String> stream = Files.lines(Paths.get(path))) {
	        //stream.forEach();
		}
        disruptor.shutdown();
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
