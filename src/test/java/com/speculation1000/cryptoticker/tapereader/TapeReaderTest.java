package com.speculation1000.cryptoticker.tapereader;

import com.speculation1000.cryptoticker.event.handler.EventEnum;
import com.speculation1000.cryptoticker.tapereader.TapeReader;
import com.speculation1000.cryptoticker.tapereader.TapeReaderImpl;
import com.speculation1000.cryptoticker.ticker.SimpleTicker;

public class TapeReaderTest {
    
    TapeReader tapeReader;
    
    public TapeReaderTest(){
        tapeReader = new TapeReaderImpl();
    }
    
    public void start() throws Exception {
        //tapeReader set ticker
    	tapeReader.setTicker(new SimpleTicker())
	        .configure("src/main/resources/application.properties")
    	    .subscribe(TapeReader.SYMBOL_FACTORY.apply(1))
    	    .subscribe(TapeReader.SYMBOL_FACTORY.apply(2))
    	    .subscribe("NXT/BTC")
    	    .subscribe("ETC/BTC")
    	    .subscribe("ETH/BTC")
    	    .addEvent(TapeReader.EVENTFACTORY.apply(EventEnum.LOG))
    	    .addEvent(TapeReader.EVENTFACTORY.apply(EventEnum.SAVE))
    	    .addEvent(TapeReader.EVENTFACTORY.apply(EventEnum.COUNT))
    	    .readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
        TapeReaderTest ticker = new TapeReaderTest();
        ticker.start();
    }
}
