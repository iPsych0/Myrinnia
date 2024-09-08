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
        OutputStream f;
        ObjectOutputStream o;
        boolean success = false;
        try {
            String path = FileUtils.getResourcePath("/savegames/save.dat");
            f = new FileOutputStream(path);

            // Disable the left-click that was pressed when selecting 'save'
            Game.get().getMouseManager().setLeftPressed(false);

            // Write the Handler object
            o = new ObjectOutputStream(f);
            o.writeObject(Handler.get());
            o.close();
            f.close();
            Handler.get().playEffect("ui/save_game.ogg");
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            Handler.get().playEffect("ui/save_game_error.ogg", 0.15f);
            Handler.get().sendMsg("WARNING: Could not save your game! Please try again or contact a developer to look into your issue!");
        }

        if (success) {
            Handler.get().sendMsg("Game successfully saved!");
        }
    }

    /**
     * Loads the game state
     */
    public static void loadHandler() {
        Handler handlerObject = null;
        ObjectInputStream oin;
        try {

            FileInputStream fis;
            String path = FileUtils.getResourcePath("/savegames/save.dat");
            fis = new FileInputStream(path);
            oin = new ObjectInputStream(fis);

            // Load in the Handler object
            handlerObject = (Handler) oin.readObject();

            oin.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Set the Handler to the loaded version
        Handler.setHandler(handlerObject);

    }
}
