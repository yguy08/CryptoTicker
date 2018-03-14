package com.tickercash.window;

import java.util.Arrays;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class ServerStarter {

    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = terminalFactory.createScreen();
        screen.startScreen();
        
        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        final Window window = new BasicWindow("Market Data Server");
        window.setHints(Arrays.asList(Hint.CENTERED));
        
        Panel contentPanel = new Panel(new GridLayout(3));
        
        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(4);
        
        Label liveFeed = new Label("Market Data");
        contentPanel.addComponent(liveFeed);
        
        Label historicalFeed = new Label("Broker");
        contentPanel.addComponent(historicalFeed);
        
        Label subscriptions = new Label("Data Source");
        contentPanel.addComponent(subscriptions);
        
        window.setComponent(contentPanel);
        textGUI.addWindowAndWait(window);
        
        
    }

}
