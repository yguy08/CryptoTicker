package com.tickercash.tapereader.gui;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class TextUiHelper {
    
    public static final void slow80sType(Screen screen, TextGraphics writer, String txt, int column, int row) throws Exception {
        for(int i = 0; i < txt.length(); i++){
            writer.setCharacter(i+column, row, txt.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
    }
    
    public static final void flash80sContinue(Screen screen, TextGraphics writer, String txt, int column, int row) throws Exception {
        KeyStroke key = null;
        do {
            writer.putString(column, row, "Press Enter to Continue, Esc to Exit...");
            screen.refresh();
            Thread.sleep(350);
            for(int i = 0; i < 100; i++){
                screen.setCharacter(i+column, row, new TextCharacter(' ',TextColor.ANSI.DEFAULT,TextColor.ANSI.BLACK));
            }
            screen.refresh();
            Thread.sleep(350);
            key = screen.pollInput();
            if(key != null){
                if(key.getKeyType() == KeyType.Escape){
                    System.exit(1);
                }else if(key.getKeyType() != KeyType.Enter){
                    key = null;
                }
            }
        }while(key == null);
    }
}
