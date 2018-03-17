package com.tickercash.window;

import java.util.Arrays;
import java.util.stream.Stream;

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
import com.tickercash.clerk.FastTickerTextUI;
import com.tickercash.enums.Broker;
import com.tickercash.enums.DataSource;
import com.tickercash.enums.Displayable;
import com.tickercash.enums.MarketDataSource;

public class ServerStarter {
    
    public static void main(String[] args) throws Exception {
    	
        DefaultTerminalFactory terminalFactory = null;
        Screen screen = null;

        try {
            terminalFactory = new DefaultTerminalFactory();
            screen = terminalFactory.createScreen();
            screen.startScreen();
            
            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("Market Data Server");
            window.setHints(Arrays.asList(Hint.CENTERED));
            
            Panel contentPanel = new Panel(new GridLayout(3));
            
            GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
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
            
            ComboBox<String> marketData = createComboBox(MarketDataSource.values());
            contentPanel.addComponent(marketData);
            
            ComboBox<String> broker = createComboBox(Broker.values());
            contentPanel.addComponent(broker);
            
            ComboBox<String> dataSource = createComboBox(DataSource.values());
            contentPanel.addComponent(dataSource);
            
            contentPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)));
            
            Button start = new Button("Start", new FastTickerTextUI(screen, window,1))
            		.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(3));
            contentPanel.addComponent(start);
            
            window.setComponent(contentPanel);
            textGUI.addWindowAndWait(window);
        }catch(Exception e) {
        	
        }finally {
        	screen.close();
        	System.exit(1);
        }
        
    }
    
    private static ComboBox<String> createComboBox(Displayable[] displayable){
        return new ComboBox<>(Stream.of(displayable).map(d -> d.getDisplayName()).toArray(String[]::new));
    }
}
