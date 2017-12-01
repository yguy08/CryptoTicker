package com.speculation1000.cryptoticker.model;

import com.speculation1000.cryptoticker.model.TapeReader;
import com.speculation1000.cryptoticker.model.TapeReaderImpl;

public class LiveTapeReaderTest {
	
	TapeReader tapeReader;
	
	String path = "src/main/java/resources/tapereader.properties";
	
	public LiveTapeReaderTest(){
		tapeReader = new TapeReaderImpl();
	}
	
	public void start() throws Exception {
		tapeReader.configure("src/main/resources/application.properties");
		tapeReader.readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
		LiveTapeReaderTest ticker = new LiveTapeReaderTest();
		ticker.start();
    }
}
