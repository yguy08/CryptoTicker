package com.tickercash.window;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import com.tickercash.clerk.FastTickerTextUI;
import com.tickercash.clerk.LiveDataClerk;
import com.tickercash.clerk.cmc.CMCQuoteBoy;
import com.tickercash.enums.Broker;
import com.tickercash.enums.DataSource;
import com.tickercash.enums.Displayable;
import com.tickercash.enums.MarketDataSource;
import com.tickercash.event.handler.MarketEventLogger;
import com.tickercash.event.handler.Transmitter;
import com.tickercash.util.TapeLogger;

public class ServerStarter {
	
	static DefaultTerminalFactory terminalFactory;
	
	static Screen screen;
	
	static WindowBasedTextGUI textGUI;
	
    static final Window window = new BasicWindow("Market Data Server");
    
    static Panel contentPanel;
    
    static GridLayout gridLayout;
    
    static ComboBox<String> marketDataComboBox;
    
    static ComboBox<String> brokerComboBox;
    
    static ComboBox<String> dataSourceComboBox;
    
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
					runSelections().run();
				}
            	
            }).setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(3));
            contentPanel.addComponent(start);
            
            window.setComponent(contentPanel);
            textGUI.addWindowAndWait(window);
        }catch(Exception e) {
        	LOGGER.error(e.getMessage());
        }finally {
        	LOGGER.error("Exit Server Starter");
        	//screen.close();
        	//System.exit(1);
        }
        
    }
    
    private static Runnable runSelections(){
    	String liveDataSel = marketDataComboBox.getSelectedItem();
    	String brokerSel = brokerComboBox.getSelectedItem();
    	String dataSourceSel = dataSourceComboBox.getSelectedItem();
    	
    	LiveDataClerk dataClerk = null;
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
    	
    	dataClerk.addHandler(new MarketEventLogger());
    	
    	try{
    		dataClerk.addHandler(new Transmitter());
    	}catch(Exception e){
    		TapeLogger.getLogger().error("MQ Start up Failed");
    	}
    	
    	return new FastTickerTextUI(screen, window, dataClerk);
    }
    
    private static ComboBox<String> createComboBox(Displayable[] displayable){
        return new ComboBox<>(Stream.of(displayable).map(d -> d.getDisplayName()).toArray(String[]::new));
    }
}
