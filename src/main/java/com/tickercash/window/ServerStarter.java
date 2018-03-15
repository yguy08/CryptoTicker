package com.tickercash.window;

import java.io.IOException;
import java.util.Arrays;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
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
import com.lmax.disruptor.EventHandler;
import com.tickercash.clerk.FakeTicker;
import com.tickercash.clerk.LiveDataClerk;
import com.tickercash.marketdata.Tick;

public class ServerStarter {
    
    private static Screen screen;
    
    private static TextGraphics writer;
    
    private static EventHandler<Tick> tick = new EventHandler<Tick>(){
        
        @Override
        public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
            writer.putString(1, 1, event.toString());
            screen.refresh();
        }
        
    };

    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        screen = terminalFactory.createScreen();
        screen.startScreen();
        
        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
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
        marketData.addItem("Noop");
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
                window.close();
                writer = screen.newTextGraphics();
                writer.setForegroundColor(TextColor.ANSI.WHITE);
                writer.setBackgroundColor(TextColor.ANSI.BLACK);
                writer.fill(' ');
                try {
                    screen.refresh();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                LiveDataClerk clerk = new FakeTicker();
                clerk.addHandler(tick);
                clerk.start();
            }
            
        });
        contentPanel.addComponent(start);
        
        
        window.setComponent(contentPanel);
        textGUI.addWindowAndWait(window);
        
        
        
        
    }
}
