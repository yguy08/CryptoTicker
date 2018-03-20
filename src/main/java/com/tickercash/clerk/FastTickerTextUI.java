package com.tickercash.clerk;

import java.io.IOException;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.lmax.disruptor.EventHandler;
import com.tickercash.clerk.cmc.CMCQuoteBoy;
import com.tickercash.event.handler.MarketEventLogger;
import com.tickercash.marketdata.MarketEvent;

public class FastTickerTextUI implements WindowClerk {
	
    private Screen screen;
    
    private TextGraphics writer;
    
    private Window window;
    
    private int throttle;
    
    private static TerminalSize terminalSize;
    
    public FastTickerTextUI(Screen screen, Window window) throws Exception {
    	this.screen = screen;
    	this.writer = screen.newTextGraphics();
    	this.window = window;
    	this.throttle = 1;
    }
    
    public FastTickerTextUI(Screen screen, Window window, int seconds) {
    	this.screen = screen;
    	this.writer = screen.newTextGraphics();
    	this.window = window;
    	this.throttle = seconds;
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
        	
            LiveDataClerk clerk = new CMCQuoteBoy(throttle);
            clerk.addHandler(TICK_EVENT);
            clerk.addHandler(new MarketEventLogger());
            clerk.start();
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
	
    private final EventHandler<MarketEvent> TICK_EVENT = new EventHandler<MarketEvent>(){
        
    	int index;
    	
        @Override
        public void onEvent(MarketEvent event, long sequence, boolean endOfBatch) throws Exception {
        	index++;
        	if(index > terminalSize.getRows()){
            	terminalSize = screen.doResizeIfNecessary();
            	if(terminalSize==null) {
            		terminalSize = screen.getTerminalSize();
            	}
            	index = 0;
            	screen.clear();
            	screen.refresh();
        	}
            writer.putString(1, index, event.toString());
            screen.refresh();
        }
        
    };

}
