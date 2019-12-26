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
            if(Handler.isJar){
                f = new FileOutputStream(Handler.jarFile.getParentFile().getAbsolutePath() + "/savegames/save.dat");
            }else {
                f = new FileOutputStream(Handler.resourcePath + "savegames/save.dat");
            }

            // Disable the left-click that was pressed when selecting 'save'
            Game.get().getMouseManager().setLeftPressed(false);

            // Write the Handler object
            o = new ObjectOutputStream(f);
            o.writeObject(Handler.get());
            o.close();
            f.close();
            Handler.get().playEffect("ui/save_game.wav");
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            Handler.get().playEffect("ui/save_game_error.wav", 0.15f);
            Handler.get().sendMsg("WARNING: Could not save your game! Please try again or contact a developer to look into your issue!");
        }

        if(success){
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
            if(Handler.isJar){
                fis = new FileInputStream(Handler.jarFile.getParentFile().getAbsolutePath() + "/savegames/save.dat");
            }else {
                fis = new FileInputStream(Handler.resourcePath + "savegames/save.dat");
            }
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
