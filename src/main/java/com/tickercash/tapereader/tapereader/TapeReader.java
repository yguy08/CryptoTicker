package com.tickercash.tapereader.tapereader;

import java.io.FileInputStream;
import java.util.Properties;

import com.tickercash.tapereader.core.Config;
import com.tickercash.tapereader.core.TickerFunction;
import com.tickercash.tapereader.event.handler.TickEventHandler;
import com.tickercash.tapereader.tape.Tape;

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

	public void addEventHandler(TickEventHandler handler) throws Exception {
		tape.addEventHandler(handler);
	}

	public void configure(String path) throws Exception {
        Config.init(path);
        
        setTape(TickerFunction.TAPEFACTORY.apply(Config.getTape()));
        
        //set events
        String[] e = Config.getEventHandlers().split(",");
        for(int i = 0; i < e.length;i++){
            addEventHandler(TickerFunction.EVENTFACTORY.apply(e[i]));
        }
        
        String[] s = Config.getSymbols().split(",");
        for(int i = 0; i < s.length;i++){
        	subscribe(s[i]);
        }
		
        tape.configure();
	}	

}
