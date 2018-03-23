package com.tickercash.window;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.ComboBox.Listener;
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
import com.tickercash.clerk.QuoteBoy;
import com.tickercash.enums.Broker;
import com.tickercash.enums.DataSource;
import com.tickercash.enums.Displayable;
import com.tickercash.enums.QuoteBoyType;
import com.tickercash.event.handler.MarketEventLogger;
import com.tickercash.event.handler.Transmitter;

public class ServerStarter {
    
    static String quoteBoy;
    
    static Screen screen;
    
    private static final Logger LOGGER = LogManager.getLogger("ServerStarter");
 
    public static void main(String[] args) throws Exception {

        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            screen = terminalFactory.createScreen();
            screen.startScreen();
            
            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            Window window = new BasicWindow("Market Data Server");
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
            
            ComboBox<String> marketDataComboBox = createComboBox(QuoteBoyType.values());
            quoteBoy = marketDataComboBox.getSelectedItem();
            marketDataComboBox.addListener(new Listener(){
                @Override
                public void onSelectionChanged(int selectedIndex, int previousSelection) {
                    quoteBoy = marketDataComboBox.getItem(selectedIndex);
                }
            });
            
            contentPanel.addComponent(marketDataComboBox);
            
            ComboBox<String> brokerComboBox = createComboBox(Broker.values());
            contentPanel.addComponent(brokerComboBox);
            
            ComboBox<String> dataSourceComboBox = createComboBox(DataSource.values());
            contentPanel.addComponent(dataSourceComboBox);
            
            contentPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)));

            Button start = new Button("Start", new Runnable(){

                @Override
                public void run() {
                    try {
                        window.close();
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
        }finally{
            screen.close();
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void startWithSelections() throws Exception {
        
        TextGraphics writer = screen.newTextGraphics();
        
        writer.setForegroundColor(TextColor.ANSI.GREEN);
        writer.setBackgroundColor(TextColor.ANSI.BLACK);
        
        screen.setCursorPosition(null);
        
        writer.fill(' ');
        screen.refresh();
        
        String init = "Initializing...";
        for(int i = 0; i < init.length(); i++){
            writer.setCharacter(i, 1, init.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
        
        String config = "Configuration:";
        for(int i = 0; i < config.length(); i++){
            writer.setCharacter(i, 3, config.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
        writer.putString(1, 5, "\t");
        writer.putString(1, 6, "\t");
        writer.putString(1, 7, "\t");
        
        String liveFeed = "Market Data Feed: "+quoteBoy.toUpperCase();
        for(int i = 0; i < liveFeed.length(); i++){
            writer.setCharacter(i+4, 5, liveFeed.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
        
        String broker = "Broker: ";
        for(int i = 0; i < broker.length(); i++){
            writer.setCharacter(i+4, 6, broker.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
        
        String dataSource = "Data Source: ";
        for(int i = 0; i < dataSource.length(); i++){
            writer.setCharacter(i+4, 7, dataSource.charAt(i));
            screen.refresh();
            Thread.sleep(10);
        }
        
        while(screen.pollInput()==null){
            writer.putString(1, 10, "Press Enter to Continue...");
            screen.refresh();
            Thread.sleep(150);
            for(int i = 0; i < 100; i++){
                screen.setCharacter(i, 10, new TextCharacter(' ',TextColor.ANSI.DEFAULT,TextColor.ANSI.BLACK));
            }
            screen.refresh();
            Thread.sleep(150);
        }
        
        QuoteBoy clerk = QuoteBoy.createQuoteBoy(quoteBoy);
        
        clerk.addHandler(new MarketEventLogger());
        clerk.addHandler(new Transmitter(clerk.toString()));
        
        
        Runnable task = () -> {
                try {
                    clerk.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        };
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    private static ComboBox<String> createComboBox(Displayable[] displayable){
        return new ComboBox<>(Stream.of(displayable).map(d -> d.getDisplayName()).toArray(String[]::new));
    }
}