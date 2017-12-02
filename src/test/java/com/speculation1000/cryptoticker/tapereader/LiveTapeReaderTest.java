package com.speculation1000.cryptoticker.tapereader;

import com.speculation1000.cryptoticker.event.handler.EventEnum;
import com.speculation1000.cryptoticker.tapereader.TapeReader;
import com.speculation1000.cryptoticker.tapereader.TapeReaderImpl;

public class LiveTapeReaderTest {
    
    TapeReader tapeReader;
    
    public LiveTapeReaderTest(){
        tapeReader = new TapeReaderImpl();
    }
    
    public void start() throws Exception {
        //tapeReader set ticker
    	tapeReader.setTicker(TapeReader.TICKER_FACTORY.apply("live"))
    	    .setTape(TapeReader.TAPE_FACTORY.apply("fake"))
    	    .subscribe(TapeReader.SYMBOL_FACTORY.apply(1))
    	    .subscribe(TapeReader.SYMBOL_FACTORY.apply(2))
    	    .addTickEvent(TapeReader.EVENT_FACTORY.apply(EventEnum.LOG))
    	    //.addTickEvent(TapeReader.EVENT_FACTORY.apply(EventEnum.SAVE))
    	    .setExchange(TapeReader.EXCHANGE_FACTORY.apply("poloniex"))
    	    .readTheTape();
    	
    	
        //tapeReader configure disruptor?
        //tapeReader start
	}
	
	public static void main(String[] args) throws Exception {
        LiveTapeReaderTest ticker = new LiveTapeReaderTest();
        ticker.start();
    }
}
