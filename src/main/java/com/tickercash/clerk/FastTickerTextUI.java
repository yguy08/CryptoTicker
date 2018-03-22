package com.tickercash.clerk;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.lmax.disruptor.EventHandler;
import com.tickercash.event.MarketDataEvent;
import com.tickercash.marketdata.MarketDataEventVO;

public class FastTickerTextUI implements WindowClerk {
    
    private Screen screen;
    
    private TextGraphics writer;
    
    private Window window;
        
    private static TerminalSize terminalSize;
    
    private LiveDataClerk clerk;
    
    public FastTickerTextUI(Screen screen, Window window, LiveDataClerk dataClerk) {
        this.screen = screen;
        this.writer = screen.newTextGraphics();
        this.window = window;
        this.clerk = dataClerk;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
    
        try {
            window.close();
            writer.setForegroundColor(TextColor.ANSI.GREEN);
            writer.setBackgroundColor(TextColor.ANSI.BLACK);
            screen.setCursorPosition(null);
            terminalSize = screen.getTerminalSize();
            screen.clear();
            screen.refresh();
            
            clerk.addHandler(TICK_EVENT);
            
            Runnable r = new Runnable(){

                @Override
                public void run() {
                    try {
                        clerk.start();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
            };
            
            r.run();
            
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally {
            try {
                screen.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    private final EventHandler<MarketDataEvent> TICK_EVENT = new EventHandler<MarketDataEvent>(){
        
        int index;
        
        @Override
        public void onEvent(MarketDataEvent event, long sequence, boolean endOfBatch) throws Exception {
            index++;
            if(index > terminalSize.getRows()){
                terminalSize = screen.doResizeIfNecessary();
                if(terminalSize==null) {
                    terminalSize = screen.getTerminalSize();
                }
                index = 0;
            }
            writer.fillRectangle(new TerminalPosition(0,terminalSize.getRows()-index), TerminalSize.ONE, ' ');
            writer.putString(1, terminalSize.getRows()-index, event.get().toString());
            screen.refresh();
        }
        
    };

}
