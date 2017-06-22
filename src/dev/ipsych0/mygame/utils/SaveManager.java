package dev.ipsych0.mygame.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemStack;

public class SaveManager {
	
	private static ArrayList<String> saveData;
	private static ArrayList<ItemStack> inventory;

	public SaveManager(Handler handler){
		saveData = new ArrayList<String>();
		inventory = new ArrayList<ItemStack>();
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
	
	public static void saveInventory(){
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File("res/savegames/inventory.dat"));
			ObjectOutputStream o;
			try {
				o = new ObjectOutputStream(f);
				for(int i = 0; i < inventory.size(); i++){
					System.out.println("Saved: "+inventory.get(i).getItem().getName() + " with a total amount of: " + inventory.get(i).getAmount());
					o.writeObject(inventory.get(i));
				}
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
	
	public static void loadInventory(Handler handler){
		ObjectInputStream inputStream = null;
        
        try {
            //Construct the ObjectInputStream object
            inputStream = new ObjectInputStream(new FileInputStream("res/savegames/inventory.dat"));
            Object itemStack = null;
            while ((itemStack = (ItemStack) inputStream.readObject()) != null) {
            	if( itemStack instanceof ItemStack){
	            	itemStack = inputStream.readObject();
	            	inventory.add((ItemStack) itemStack);
            	}
            }
         
        } catch (EOFException ex) {  //This exception will be caught when EOF is reached
            System.out.println("End of file reached.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the ObjectInputStream
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	
		/*
		 * Set the Inventory items
		 */
		System.out.println("Inventory size = "+ inventory.size());
		for(int i = 0; i < inventory.size(); i++){
			handler.getWorld().getInventory().getItemSlots().get(i).setItem(inventory.get(i));
		}
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
	
	public static void addInventoryItems(ItemStack itemStack){
		inventory.add(itemStack);
	}
	
	public static void clearSaveData(){
		saveData.clear();
	}
	
	public static void clearInventoryItems(){
		inventory.clear();
	}
	
	public static ArrayList<String> getSaveData() {
		return saveData;
	}
	
	public static ArrayList<ItemStack> getInventoryItems(){
		return inventory;
	}
}
