package com.tickercash.window;

import java.util.stream.Collectors;
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
		
		TextGraphics tGraphics = screen.newTextGraphics();
		
		screen.startScreen();
		screen.clear();
		screen.setCursorPosition(null);
		
		String welcome = Stream.of(args).collect(Collectors.joining(" "));
		for(int i = 0; i < welcome.length(); i++){
			tGraphics.setCharacter(i, 1, welcome.charAt(i));
			slowType();
			screen.refresh();
		}

		screen.readInput();
		screen.stopScreen();
		screen.close();

	}
	
	private static void slowType() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
