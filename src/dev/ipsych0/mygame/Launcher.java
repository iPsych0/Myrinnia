package dev.ipsych0.mygame;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Launcher {
	
	/*
	 * Starts the game loop
	 */
	public static void main(String[] args){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		Game game = new Game("Elements of Myrinnia Pre-Alpha Development v0.7", (int)width, (int)height);
		
//		Game game = new Game("Elements of Myrinnia Pre-Alpha Development v0.7", 960, 720);
		game.start();
	}

}
