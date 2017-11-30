package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.model.TapeReader;
import com.speculation1000.cryptoticker.model.TapeReaderImpl;
import com.speculation1000.cryptoticker.tape.CsvTape;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.ticker.HistoricalTicker;
import com.speculation1000.cryptoticker.ticker.Ticker;

public class TapeReplayTest {
	
	TapeReader tapeReader;
	
	Ticker ticker;
	
	Tape dataFeed;
	
	public TapeReplayTest(){
		tapeReader = new TapeReaderImpl();
		ticker = new HistoricalTicker();
		dataFeed = new CsvTape();
	}
	
	public void start() throws Exception {
		dataFeed.configure("src/main/resources/application.properties");
		ticker.setTape(dataFeed);
		tapeReader.setTicker(ticker);
		tapeReader.readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
		TapeReplayTest ticker = new TapeReplayTest();
		ticker.start();
    }
}
