package dev.ipsych0.mygame;

public class Launcher {
	
	/*
	 * Starts the game loop
	 */
	public static void main(String[] args){
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		double width = screenSize.getWidth();
//		double height = screenSize.getHeight();
//		Game game = new Game("Myrinnia Pre-Alpha Development v0.4", (int)width, (int)height);
		
		Game game = new Game("Myrinnia Pre-Alpha Development v0.55", 960, 720);
		game.start();
	}

}
