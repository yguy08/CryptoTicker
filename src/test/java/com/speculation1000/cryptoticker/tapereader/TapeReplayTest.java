package com.speculation1000.cryptoticker.tapereader;

import com.speculation1000.cryptoticker.tape.CsvTape;
import com.speculation1000.cryptoticker.tape.Tape;
import com.speculation1000.cryptoticker.tapereader.TapeReader;
import com.speculation1000.cryptoticker.tapereader.TapeReaderImpl;
import com.speculation1000.cryptoticker.ticker.HistoricalTicker;
import com.speculation1000.cryptoticker.ticker.Ticker;

public class TapeReplayTest {
	
	TapeReader tapeReader;
	
	Ticker ticker;
	
	Tape tape;
	
	public TapeReplayTest(){
		tapeReader = new TapeReaderImpl();
		ticker = new HistoricalTicker();
		tape = new CsvTape();
	}
	
	public void start() throws Exception {
		tapeReader.setTicker(ticker);
		tapeReader.setTape(tape);
	}
	
	public static void main(String[] args) throws Exception {
		TapeReplayTest ticker = new TapeReplayTest();
		ticker.start();
    }
}
