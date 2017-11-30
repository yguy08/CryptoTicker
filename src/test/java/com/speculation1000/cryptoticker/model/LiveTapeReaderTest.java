package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.model.TapeReader;
import com.speculation1000.cryptoticker.model.TapeReaderImpl;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.tape.XchangeLiveTape;
import com.speculation1000.cryptoticker.ticker.LiveTicker;
import com.speculation1000.cryptoticker.ticker.Ticker;

public class LiveTapeReaderTest {
	
	TapeReader tapeReader;
	
	Ticker ticker;
	
	Tape tape;
	
	public LiveTapeReaderTest(){
		tapeReader = new TapeReaderImpl();
		ticker = new LiveTicker();
		tape = new XchangeLiveTape();
	}
	
	public void start() throws Exception {
		tape.configure("src/main/resources/application.properties");
		ticker.setTape(tape);
		tapeReader.setTicker(ticker);
		tapeReader.readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
		LiveTapeReaderTest ticker = new LiveTapeReaderTest();
		ticker.start();
    }
}
