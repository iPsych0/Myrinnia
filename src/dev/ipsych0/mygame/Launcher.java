package dev.ipsych0.mygame;

public class Launcher {
	
	/*
	 * Starts the game loop
	 */
	public static void main(String[] args){
		Game game = new Game("Myrinnia Pre-Alpha Development v0.4", 960, 720);
		game.start();
	}

}
