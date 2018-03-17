package com.tickercash.clerk;

import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.lmax.disruptor.EventHandler;
import com.tickercash.clerk.cmc.CMCQuoteBoy;
import com.tickercash.marketdata.Tick;

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
        	
        	writer.fill('B');
        	Thread.sleep(100);
        	writer.fill('T');
        	Thread.sleep(100);
        	writer.fill('C');
        	Thread.sleep(100);
        	
        	screen.clear();
        	
        	
            LiveDataClerk clerk = new CMCQuoteBoy(throttle);
            clerk.addHandler(TICK_EVENT);
            clerk.addHandler(LOG_TICK_EVENT);
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
	
    private final EventHandler<Tick> TICK_EVENT = new EventHandler<Tick>(){
        
    	final Random random = new Random();
    	
        @Override
        public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        	terminalSize = screen.doResizeIfNecessary();
        	if(terminalSize==null) {
        		terminalSize = screen.getTerminalSize();
        	}
            writer.putString(random.nextInt(terminalSize.getColumns()), random.nextInt(terminalSize.getRows()), event.toString());
            screen.refresh();
        }
        
    };
    
    private final EventHandler<Tick> LOG_TICK_EVENT = new EventHandler<Tick>(){
        
    	final Logger LOGGER = LogManager.getLogger("CMCQuoteBoy");

    	@Override
        public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
    		LOGGER.info("New Tick:  {}", event.toString());
        }
        
    };

}
