package com.tickercash.window;

import java.util.stream.Stream;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class ServerStarter {

	public static void main(String[] args) throws Exception {
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(terminal);

		String liveService = args[0];
		TextGraphics tGraphics = screen.newTextGraphics();

		screen.startScreen();
		screen.clear();
		screen.setCursorPosition(null);
		
		for(int i = 0; i < args[0].length(); i++) {
			tGraphics.setCharacter(i, 1, args[0].charAt(i));
			slowType();
			screen.refresh();
		}
		
		screen.refresh();

		screen.readInput();
		screen.stopScreen();

	}
	
	private static void slowType() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
