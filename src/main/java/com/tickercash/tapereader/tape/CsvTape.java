package com.tickercash.tapereader.tape;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import net.openhft.chronicle.bytes.Bytes;

public class CsvTape extends Tape {
	
    private String path;
	
	private BufferedReader reader;
	
	private Bytes<?> bytes = Bytes.elasticByteBuffer();
	
	@Override
	public void start() throws Exception {
		disruptor.start();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        onTick(bytes.append(line));
	    }
        disruptor.shutdown();
	}

	@Override
	public void configure(Properties prop) throws Exception {
        setFilePath(prop.getProperty("csv.file"));
        reader = new BufferedReader(new FileReader(path));	    	
	}
	
    private void setFilePath(String property) {
		path = property;
	}
}
