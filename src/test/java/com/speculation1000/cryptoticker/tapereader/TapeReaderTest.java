package com.speculation1000.cryptoticker.tapereader;

import com.speculation1000.cryptoticker.tapereader.TapeReader;

public class TapeReaderTest {
    
    TapeReader tapeReader;
    
    public TapeReaderTest(){
        tapeReader = new TapeReader();
    }
    
    public void start() throws Exception {
        //tapeReader set ticker
    	tapeReader.configure("src/main/resources/application.properties");
    	
    	tapeReader.readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
        TapeReaderTest ticker = new TapeReaderTest();
        ticker.start();
    }
}
