package com.speculation1000.cryptoticker.tape;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import com.speculation1000.cryptoticker.core.TickerFunction;
import com.speculation1000.cryptoticker.event.processor.EventProcessor;

import net.openhft.chronicle.bytes.Bytes;

public class CsvTapeCopyReplace extends Tape {
	
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
	public void addEventHandler(EventProcessor handler) {
		disruptor.handleEventsWith(handler::onEvent);
        tickEvents.add(handler);
	}

	@Override
	public void configure(Properties prop) throws Exception {        
        setFilePath(prop.getProperty("csv.file"));
        
        String[] s = prop.getProperty("event.processor").split(",");
        for(int i = 0; i < s.length;i++){
            addEventHandler(TickerFunction.EVENTFACTORY.apply(s[i]));
        }  

        for(EventProcessor ep : tickEvents){
            ep.configure(prop);
        }
        
        reader = new BufferedReader(new FileReader(path));
        
	}
	
    private void setFilePath(String path) {
		this.path = path;
	}

}
