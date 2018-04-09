package com.tickercash.tapereader.gui;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.tickercash.tapereader.bucketshop.BucketShopType;
import com.tickercash.tapereader.ticker.QuoteBoyType;
import com.googlecode.lanterna.gui2.ComboBox.Listener;
import com.googlecode.lanterna.gui2.Window.Hint;

public class ServerTUI extends ScreenBase {
    
    private String quoteBoy;
    
    private String broker;
    
    private String tape;
    
    private static final Logger LOGGER = LogManager.getLogger("ServerTUI");

    public ServerTUI() throws Exception {
        super("Tape Reader");
    }

    @Override
    public void init() throws Exception {
        try {
            
            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            Window window = new BasicWindow("Tape Reader - Server");
            window.setHints(Arrays.asList(Hint.CENTERED));
            
            Panel contentPanel = new Panel(new GridLayout(3));
            
            GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
            gridLayout.setHorizontalSpacing(3);
            
            contentPanel.addComponent(
            new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)));
            
            Label liveFeed = new Label("Quote Boy");
            contentPanel.addComponent(liveFeed);
            
            Label historicalFeed = new Label("Broker");
            contentPanel.addComponent(historicalFeed);
            
            Label dataSourceLbl = new Label("Tape");
            contentPanel.addComponent(dataSourceLbl);
            
            contentPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)));
            
            //combo boxes
            createComboBoxes(contentPanel);
            
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
    
    private void startWithSelections() throws Exception {
        
        TextGraphics writer = screen.newTextGraphics();
        
        writer.setForegroundColor(TextColor.ANSI.GREEN);
        writer.setBackgroundColor(TextColor.ANSI.BLACK);
        
        screen.setCursorPosition(null);
        
        writer.fill(' ');
        screen.refresh();
        
        String init = "Initializing...";
        TextUiHelper.slow80sType(screen, writer, init, 1, 1);
        
        String config = "Configuration:";
        TextUiHelper.slow80sType(screen, writer, config, 1, 3);
        
        writer.putString(1, 5, "\t");
        writer.putString(1, 6, "\t");
        writer.putString(1, 7, "\t");
        
        String liveFeed = "Market Data Feed: "+quoteBoy.toUpperCase();
        TextUiHelper.slow80sType(screen, writer, liveFeed, 4, 5);
        
        String brokerSel = "Broker: "+broker.toUpperCase();
        TextUiHelper.slow80sType(screen, writer, brokerSel, 4, 6);
        
        String dataSourceSel = "Data Source: "+tape.toUpperCase();
        TextUiHelper.slow80sType(screen, writer, dataSourceSel, 4, 7);

        TextUiHelper.flash80sContinue(screen, writer, "Press Enter to Continue, Esc to Exit...", 1, 10);
    }
    
    private void createComboBoxes(Panel contentPanel){
        //Quote Boy
        ComboBox<String> quoteBoyComboBox = createComboBoxList(QuoteBoyType.values());
        quoteBoy = quoteBoyComboBox.getSelectedItem();
        quoteBoyComboBox.addListener(new Listener(){
            @Override
            public void onSelectionChanged(int selectedIndex, int previousSelection) {
                quoteBoy = quoteBoyComboBox.getItem(selectedIndex);
            }
        });
        contentPanel.addComponent(quoteBoyComboBox);
        //Broker
        ComboBox<String> brokerComboBox = createComboBoxList(BucketShopType.values());
        broker = brokerComboBox.getSelectedItem();
        brokerComboBox.addListener(new Listener(){
            @Override
            public void onSelectionChanged(int selectedIndex, int previousSelection) {
                broker = brokerComboBox.getItem(selectedIndex);
            }
        });
        contentPanel.addComponent(brokerComboBox);
    }

    private static ComboBox<String> createComboBoxList(Displayable[] displayable){
        return new ComboBox<>(Stream.of(displayable).map(d -> d.getDisplayName()).toArray(String[]::new));
    }

}
