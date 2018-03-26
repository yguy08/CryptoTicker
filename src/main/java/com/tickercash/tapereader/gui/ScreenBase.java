package com.tickercash.tapereader.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class ScreenBase {
	
	private Screen screen;
	
    private static final Logger LOGGER = LogManager.getLogger("ScreenBase");
	
	public ScreenBase() {
        try {
        	screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();
        }catch(Exception e) {
            LOGGER.error(e);
        }
	}
	
	public Screen getScreen() {
		return screen;
	}

}
