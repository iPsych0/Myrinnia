package dev.ipsych0.mygame;

public class Launcher {
	
	/*
	 * Starts the game loop
	 */
	public static void main(String[] args){
		System.setProperty("sun.java2d.opengl", "True");
		
		Game game = Game.get();
		game.start();
	}

}
