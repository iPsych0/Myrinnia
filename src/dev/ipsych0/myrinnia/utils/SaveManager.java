package dev.ipsych0.myrinnia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dev.ipsych0.myrinnia.Game;
import dev.ipsych0.myrinnia.Handler;


public class SaveManager {
	
	private SaveManager() {
		
	}
	
	/**
	 * Saves the game state
	 */
	public static void savehandler(){
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the game state
	 */
	public static void loadHandler(){
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Set the Handler to the loaded version
		Handler.setHandler(handlerObject);

	}
}
