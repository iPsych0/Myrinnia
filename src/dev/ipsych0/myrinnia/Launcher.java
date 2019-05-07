package dev.ipsych0.myrinnia;

class Launcher {

    /*
     * Starts the game loop
     */
    public static void main(String[] args) {

        // Runtime JVM arguments
        System.setProperty("sun.java2d.opengl", "true");

        // Starts the game
        Game game = Game.get();
        game.start();
    }

}
