package com.speculation1000.cryptoticker.tapereader;

import com.speculation1000.cryptoticker.core.TickerFunction;
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
    	tapeReader.configure("src/main/resources/application.properties")
    	    .subscribe(TickerFunction.SYMBOLFACTORY.apply(1))
    	    .subscribe(TickerFunction.SYMBOLFACTORY.apply(2))
    	    .subscribe("NXT/BTC")
    	    .subscribe("ETC/BTC")
    	    .subscribe("ETH/BTC")
    	    .addEvent(TickerFunction.EVENTFACTORY.apply(EventEnum.LOG))
    	    .addEvent(TickerFunction.EVENTFACTORY.apply(EventEnum.SAVE))
    	    .addEvent(TickerFunction.EVENTFACTORY.apply(EventEnum.COUNT))
    	    .readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
        TapeReaderTest ticker = new TapeReaderTest();
        ticker.start();
    }
}
