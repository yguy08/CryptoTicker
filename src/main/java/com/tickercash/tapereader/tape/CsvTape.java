package com.tickercash.tapereader.tape;

import java.io.BufferedReader;
import java.io.FileReader;
import com.tickercash.tapereader.core.Config;

public class CsvTape extends Tape {
	
    private String path;
	
	private BufferedReader reader;
		
	@Override
	public void start() throws Exception {
		disruptor.start();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        //onTick(bytes.append(line));
	    }
        disruptor.shutdown();
	}

	@Override
	public void configure() throws Exception {
        setFilePath(Config.getCsvFile());
        reader = new BufferedReader(new FileReader(path));	    	
	}
	
    private void setFilePath(String property) {
		path = property;
	}
}
