package com.tickercash.window;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.tickercash.clerk.FakeTicker;
import com.tickercash.clerk.QuoteBoy;
import com.tickercash.clerk.cmc.CMCQuoteBoy;
import com.tickercash.enums.Broker;
import com.tickercash.enums.DataSource;
import com.tickercash.enums.Displayable;
import com.tickercash.enums.MarketDataSource;
import com.tickercash.event.handler.MarketEventLogger;
import com.tickercash.event.handler.Transmitter;

public class ServerStarter {
	
	static DefaultTerminalFactory terminalFactory;
	
	static Screen screen;
	
	static WindowBasedTextGUI textGUI;
	
	private static final Window window = new BasicWindow("Market Data Server");
    
    private static Panel contentPanel;
    
    private static GridLayout gridLayout;
    
    private static ComboBox<String> marketDataComboBox;
    
    private static ComboBox<String> brokerComboBox;
    
    private static ComboBox<String> dataSourceComboBox;
    
    private static final Logger LOGGER = LogManager.getLogger("ServerStarter");
 
    public static void main(String[] args) throws Exception {

        try {
            terminalFactory = new DefaultTerminalFactory();
            screen = terminalFactory.createScreen();
            screen.startScreen();
            
            textGUI = new MultiWindowTextGUI(screen);
            window.setHints(Arrays.asList(Hint.CENTERED));
            
            contentPanel = new Panel(new GridLayout(3));
            
            gridLayout = (GridLayout)contentPanel.getLayoutManager();
            gridLayout.setHorizontalSpacing(3);
            
            contentPanel.addComponent(
            new EmptySpace()
                    .setLayoutData(
                            GridLayout.createHorizontallyFilledLayoutData(3)));
            
            Label liveFeed = new Label("Market Data");
            contentPanel.addComponent(liveFeed);
            
            Label historicalFeed = new Label("Broker");
            contentPanel.addComponent(historicalFeed);
            
            Label dataSourceLbl = new Label("Data Source");
            contentPanel.addComponent(dataSourceLbl);
            
            contentPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)));
            
            marketDataComboBox = createComboBox(MarketDataSource.values());
            contentPanel.addComponent(marketDataComboBox);
            
            brokerComboBox = createComboBox(Broker.values());
            contentPanel.addComponent(brokerComboBox);
            
            dataSourceComboBox = createComboBox(DataSource.values());
            contentPanel.addComponent(dataSourceComboBox);
            
            contentPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)));
            
            Button start = new Button("Start", new Runnable(){

				@Override
				public void run() {
					try {
						startWithSelections();
					} catch (Exception e) {
						LOGGER.error(e.getMessage());
					}
				}
            	
            }).setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(3));
            contentPanel.addComponent(start);
            
            window.setComponent(contentPanel);
            textGUI.addWindowAndWait(window);
        }catch(Exception e) {
        	LOGGER.error(e.getMessage());
        }
        
    }
    
    private static void startWithSelections() throws Exception {
    	window.close();
    	
        TextGraphics writer = screen.newTextGraphics();
        
        writer.setForegroundColor(TextColor.ANSI.GREEN);
        writer.setBackgroundColor(TextColor.ANSI.BLACK);
        
        screen.setCursorPosition(null);
        
        writer.fill(' ');
        writer.putString(1, 1, "Want to run...");
        writer.putString(1, 5, "\tMarket Data Feed: "+marketDataComboBox.getSelectedItem());
        
        screen.refresh();
        
        while(screen.pollInput()==null){
        	writer.putString(1, 10, "Press Enter to Continue...");
            screen.refresh();
        	Thread.sleep(100);
        	for(int i = 0; i < 100; i++){
        		screen.setCharacter(i, 10, new TextCharacter(
                        ' ',
                        TextColor.ANSI.DEFAULT,
                        // This will pick a random background color
                        TextColor.ANSI.BLACK));
        	}
            screen.refresh();
        	Thread.sleep(100);
        }
    	
        //Timer would be cool here...progress bar
		QuoteBoy clerk = getLiveDataClerk();
		clerk.addHandler(new MarketEventLogger());
		try {
			clerk.addHandler(new Transmitter());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        Runnable r = new Runnable(){

			@Override
			public void run() {
				try {
					clerk.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        	
        };
        
        Thread t = new Thread(r);
        t.start();
        
        ClientStarter client = new ClientStarter();
            
        String next = null;
        while(screen.pollInput()==null){
        		next = client.nextMessage();
        		if(next!= null){
                	writer.putString(1, 10, next);
                    screen.refresh();
                	//Thread.sleep(100);
                	for(int i = 0; i < 100; i++){
                		screen.setCharacter(i, 10, new TextCharacter(
                                ' ',
                                TextColor.ANSI.DEFAULT,
                                // This will pick a random background color
                                TextColor.ANSI.BLACK));
                	}
                    screen.refresh();
        		}
        	
        }
        
        screen.close();
        
    }
    
    private static QuoteBoy getLiveDataClerk(){
    	String liveDataSel = marketDataComboBox.getSelectedItem();
    	QuoteBoy dataClerk = null;
    	switch(MarketDataSource.valueOf(liveDataSel.toUpperCase())) {
    	case CMC:
    		dataClerk = new CMCQuoteBoy();
    		break;
    	case POLONIEX:
    	case GDAX:
    	case FAKE:
    		dataClerk = new FakeTicker();
    		break;
    	default:
    		dataClerk = new FakeTicker();
    		break;
    	}
    	return dataClerk;
    }
    
    private static ComboBox<String> createComboBox(Displayable[] displayable){
        return new ComboBox<>(Stream.of(displayable).map(d -> d.getDisplayName()).toArray(String[]::new));
    }
}
