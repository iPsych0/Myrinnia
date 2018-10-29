package dev.ipsych0.myrinnia;

public class Launcher {
	
	/*
	 * Starts the game loop
	 */
	public static void main(String[] args){
		
		 // Runtime JVM arguments
		System.setProperty("sun.java2d.opengl", "True");
		
		// Starts the game
		Game game = Game.get();
		game.start();
	}

}
