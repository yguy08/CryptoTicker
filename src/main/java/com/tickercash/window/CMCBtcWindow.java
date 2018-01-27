package com.tickercash.window;

import java.io.IOException;
import com.tickercash.tapereader.CMCBtc;

public class CMCBtcWindow {
	
	public static void main(String[] args) throws IOException {
        new CMCBtc().readTheTape();
	}

}
