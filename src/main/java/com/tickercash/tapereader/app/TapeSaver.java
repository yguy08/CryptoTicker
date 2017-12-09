package com.tickercash.tapereader.app;

import com.tickercash.tapereader.tapereader.TapeReader;

public class TapeSaver {

	public static void main(String[] args) {
        TapeReader tapeReader = new TapeReader();
        
        try{
            tapeReader.configure("src/main/resources/application.properties");
            tapeReader.readTheTape();
        }catch(Exception e){
        	
        }
    }

}
