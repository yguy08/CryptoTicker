package com.tickercash.tapereader.tapereader;

import com.tickercash.tapereader.tapereader.TapeReader;

public class TapeReaderTest {
    
    TapeReader tapeReader;
    
    public TapeReaderTest(){
        tapeReader = new TapeReader();
    }
    
    public void start() throws Exception {
        //tapeReader set ticker
    	tapeReader.configure("application.properties");
    	
    	tapeReader.readTheTape();
	}
	
	public static void main(String[] args) throws Exception {
        TapeReaderTest ticker = new TapeReaderTest();
        ticker.start();
    }
}
