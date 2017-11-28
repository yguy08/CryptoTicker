package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.model.DataFeed;
import com.speculation1000.cryptoticker.model.LiveTicker;
import com.speculation1000.cryptoticker.model.TapeReader;
import com.speculation1000.cryptoticker.model.TapeReaderImpl;
import com.speculation1000.cryptoticker.model.XchangeDataFeed;

public class LiveTapeReaderTest {
	
	TapeReader tapeReader;
	
	Ticker ticker;
	
	DataFeed dataFeed;
	
	public LiveTapeReaderTest(){
		tapeReader = new TapeReaderImpl();
		ticker = new LiveTicker();
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
