package com.tickercash.window;

import java.io.IOException;
import java.util.Arrays;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.lmax.disruptor.EventHandler;
import com.tickercash.clerk.LiveDataClerk;
import com.tickercash.clerk.cmc.CMCQuoteBoy;
import com.tickercash.marketdata.Tick;

public class ServerStarter {
	
	private static Terminal terminal;
	
	private static Screen screen;
	
	private static WindowBasedTextGUI textGUI;
	
	private static int count = 0;
	
	private static EventHandler<Tick> tick = new EventHandler<Tick>(){
		
		@Override
		public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
			screen.newTextGraphics().putString(1, 1, count++ + event.toString());
			screen.refresh();
		}
		
	};

    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        screen = terminalFactory.createScreen();
        screen.startScreen();
        
        textGUI = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Market Data Server");
        window.setHints(Arrays.asList(Hint.CENTERED));
        
        Panel contentPanel = new Panel(new GridLayout(3));
        
        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(4);
        
        Label liveFeed = new Label("Market Data");
        contentPanel.addComponent(liveFeed);
        
        Label historicalFeed = new Label("Broker");
        contentPanel.addComponent(historicalFeed);
        
        Label dataSourceLbl = new Label("Data Source");
        contentPanel.addComponent(dataSourceLbl);
        
        ComboBox<String> marketData = new ComboBox<>();
        marketData.addItem("Poloniex");
        marketData.addItem("GDAX");
        marketData.addItem("Binance");
        marketData.addItem("Kucoin");
        contentPanel.addComponent(marketData);
        
        ComboBox<String> broker = new ComboBox<>();
        broker.addItem("N/A");
        contentPanel.addComponent(broker);
        
        ComboBox<String> dataSource = new ComboBox<>();
        dataSource.addItem("N/A");
        dataSource.addItem("H2 DB");
        dataSource.addItem("CSV");
        contentPanel.addComponent(dataSource);
        
        Button start = new Button("Start", new Runnable() {

			@Override
			public void run() {
				try {
					LiveDataClerk data = new CMCQuoteBoy();
					data.addHandler(tick);
					data.start();
					screen.clear();
					screen.refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						terminal.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
        	
        });
        contentPanel.addComponent(start);
        
        
        window.setComponent(contentPanel);
        textGUI.addWindowAndWait(window);
        
        
        
        
    }

}
