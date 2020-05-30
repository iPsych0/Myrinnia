package dev.ipsych0.myrinnia;

import dev.ipsych0.splashscreen.SplashScreen;

class Launcher {

    /*
     * Starts the game loop
     */
    public static void main(String[] args) {

        // Runtime JVM arguments
        System.setProperty("sun.java2d.opengl", "true");

        // Show splash screen while loading game
        new SplashScreen();

        // Starts the game
        Game game = Game.get();
        game.start();
    }

}
