package com.tickercash.tapereader.gui;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class TextGuiUtils {
	
	public static final void slow80sType(Screen screen, TextGraphics writer, String text, int row) throws Exception {
        for(int i = 0; i < text.length(); i++){
            writer.setCharacter(i, row, text.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
	}
	
	public static final void slow80sType(Screen screen, TextGraphics writer, String text, int row, int column) throws Exception {
        for(int i = 0; i < text.length(); i++){
            writer.setCharacter(i+column, row, text.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
	}
	
	public static final void flash80sContinue(Screen screen, TextGraphics writer, String text, int row, int column) throws Exception {
        KeyStroke input = null;
        do {
            writer.putString(column, row, "Press Enter to Continue, ESC to Exit...");
            screen.refresh();
            Thread.sleep(250);
            for(int i = 0; i < 100; i++){
                screen.setCharacter(i, row, new TextCharacter(' ',TextColor.ANSI.DEFAULT,TextColor.ANSI.BLACK));
            }
            screen.refresh();
            Thread.sleep(250);
            input = screen.pollInput();
        	if(input!= null) {
        		if(input.getKeyType().equals(KeyType.Escape)) {
        			System.exit(0);
        		}else if(!input.getKeyType().equals(KeyType.Enter)) {
                	input = null;
                }
        	}        	
        }while(input == null);
	}

}
