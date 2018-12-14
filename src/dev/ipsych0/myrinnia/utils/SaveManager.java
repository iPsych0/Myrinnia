package dev.ipsych0.myrinnia.utils;

import dev.ipsych0.myrinnia.Game;
import dev.ipsych0.myrinnia.Handler;

import java.io.*;


public class SaveManager {

    private SaveManager() {

    }

    /**
     * Saves the game state
     */
    public static void savehandler() {
        FileOutputStream f;
        ObjectOutputStream o;
        try {
            f = new FileOutputStream(new File("res/savegames/save.dat"));

            // Disable the left-click that was pressed when selecting 'save'
            Game.get().getMouseManager().setLeftPressed(false);

            // Write the Handler object
            o = new ObjectOutputStream(f);
            o.writeObject(Handler.get());
            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
            Handler.get().sendMsg("WARNING: Could not save your game, please try again!");
        }
    }

    /**
     * Loads the game state
     */
    public static void loadHandler() {
        Handler handlerObject = null;
        FileInputStream fin;
        ObjectInputStream oin;
        try {
            fin = new FileInputStream("res/savegames/save.dat");
            oin = new ObjectInputStream(fin);

            // Load in the Handler object
            handlerObject = (Handler) oin.readObject();

            oin.close();
            fin.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Set the Handler to the loaded version
        Handler.setHandler(handlerObject);

    }
}
