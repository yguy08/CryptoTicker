package com.speculation1000.cryptoticker.tapereader;

import java.io.FileInputStream;
import java.util.Properties;

import com.speculation1000.cryptoticker.core.TickerFunction;
import com.speculation1000.cryptoticker.event.handler.EventHandler;
import com.speculation1000.cryptoticker.tape.Tape;

public class TapeReader {

    private Tape tape;
    
    private Properties config;

	public void readTheTape() throws Exception {
		tape.start();
	}

	public void setTape(Tape t) {
		tape = t;
	}

	public void subscribe(String symbol) {
		tape.subscribe(symbol);
	}

	public void addEventHandler(EventHandler handler) {
		tape.addEventHandler(handler);
	}

	public void configure(String path) throws Exception {
        config = new Properties();
        config.load(new FileInputStream(path));
        
        setTape(TickerFunction.TAPEFACTORY.apply(config.getProperty("tape")));
        
        //set events
        String[] s = config.getProperty("event.handler").split(",");
        for(int i = 0; i < s.length;i++){
            addEventHandler(TickerFunction.EVENTFACTORY.apply(s[i]));
        }
		
        tape.configure(config);
	}	

}
