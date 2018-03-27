package com.tickercash.tapereader.gui;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public abstract class ScreenBase {
    
    protected Screen screen;

    public ScreenBase(String title) throws Exception{
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorTitle(title);
        screen = terminalFactory.createScreen();
        screen.startScreen();
    }
    
    public abstract void init() throws Exception;
    
    public Screen getScreen(){
        return screen;
    }

}
