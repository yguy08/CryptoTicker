package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.model.Tape;
import com.speculation1000.cryptoticker.model.LiveTape;
import com.speculation1000.cryptoticker.model.TapeReader;
import com.speculation1000.cryptoticker.model.TapeReaderImpl;
import com.speculation1000.cryptoticker.model.XchangeDataFeed;

public class LiveTapeReaderTest {
	
	TapeReader tapeReader;
	
	Ticker ticker;
	
	Tape dataFeed;
	
	public LiveTapeReaderTest(){
		tapeReader = new TapeReaderImpl();
		ticker = new LiveTape();
		dataFeed = new XchangeDataFeed();
	}
	
	public void start() throws Exception {
		dataFeed.configure("src/main/resources/application.properties");
		ticker.setDataFeed(dataFeed);
		tapeReader.setTicker(ticker);
		tapeReader.readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
		LiveTapeReaderTest ticker = new LiveTapeReaderTest();
		ticker.start();
    }
}
