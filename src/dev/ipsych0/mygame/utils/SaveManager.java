package dev.ipsych0.mygame.utils;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import dev.ipsych0.mygame.Game;
import dev.ipsych0.mygame.Handler;


public class SaveManager {
	
	public static void savehandler(){
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File("res/savegames/save.dat"));
			ObjectOutputStream o;
			try {
				Game.get().getMouseManager().setLeftPressed(false);
				o = new ObjectOutputStream(f);
					o.writeObject(Handler.get());
				o.close();
				f.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadHandler(){
		Handler handlerObject = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/save.dat");
			ObjectInputStream oin = new ObjectInputStream(fin);
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
		
		Handler.setHandler(handlerObject);

	}
}
