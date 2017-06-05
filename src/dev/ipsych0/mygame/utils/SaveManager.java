package dev.ipsych0.mygame.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;

public class SaveManager {
	
	private static ArrayList<String> saveData;

	public SaveManager(Handler handler){
		saveData = new ArrayList<String>();
	}

	public static void saveGame(){
		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream("res/savegames/save.dat"));
			for (int i = 0; i < saveData.size(); i++) {
				try {
					// write down all the saved data stored in the Array
					output.writeUTF(saveData.get(i));
					System.out.println("Saved data: " + saveData.get(i));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				// close output after writing
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// hier save code
	}
	
	public static void loadGame(Handler handler){
		/*
		 * File Loading stuff
		 */
		try {
			DataInputStream input = new DataInputStream(new FileInputStream("res/savegames/save.dat"));
			try {
				while(input.available() > 0){
					saveData.add(input.readUTF());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				// Close loading inputstream
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		/*
		 * Setting variables
		 */
		
		handler.getWorld().getEntityManager().getPlayer().setAttackExperience(Integer.valueOf(saveData.get(0)));
		handler.getWorld().getEntityManager().getPlayer().setX(Float.valueOf(saveData.get(1)));
		handler.getWorld().getEntityManager().getPlayer().setY(Float.valueOf(saveData.get(2)));
		handler.getWorld().getEntityManager().getPlayer().setHealth(Integer.valueOf(saveData.get(3)));

	}
	
	public static void addSaveData(String data){
		saveData.add(data);
	}
	
	public static void clearSaveData(){
		saveData.clear();
	}
	
	public static ArrayList<String> getSaveData() {
		return saveData;
	}
}
