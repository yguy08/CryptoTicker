package com.speculation1000.cryptoticker.app;

import com.speculation1000.cryptoticker.core.TickerFunction;
import com.speculation1000.cryptoticker.event.handler.EventEnum;
import com.speculation1000.cryptoticker.tapereader.TapeReader;
import com.speculation1000.cryptoticker.tapereader.TapeReaderImpl;

public class TapeSaver {

	private static TapeReader tapeReader = new TapeReaderImpl();

	public static void main(String[] args) throws Exception {
        try{
        	run();
        }catch (Exception e){
        	Thread.sleep(5000);
        	run();
        }
    }
	
	public static void run() throws Exception {
		tapeReader.configure("src/main/resources/application.properties")
		.addEvent(TickerFunction.EVENTFACTORY.apply(EventEnum.SAVE))
		.readTheTape();
	}

}
