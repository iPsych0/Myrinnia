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

import javax.imageio.ImageIO;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.EquipmentStack;
import dev.ipsych0.mygame.items.ItemStack;

public class SaveManager {
	
	private static ArrayList<String> saveData;
	private static ArrayList<ItemStack> inventory;
	private static ArrayList<EquipmentStack> equipment;

	public SaveManager(Handler handler){
		saveData = new ArrayList<String>();
		inventory = new ArrayList<ItemStack>();
		equipment = new ArrayList<EquipmentStack>();
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
					o.writeObject(inventory);
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
	
	public static void saveEquipment(){
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File("res/savegames/equipment.dat"));
			ObjectOutputStream o;
			try {
				o = new ObjectOutputStream(f);
					o.writeObject(equipment);
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
	
	public static void loadInventory(Handler handler){
		inventory = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/inventory.dat");
			ObjectInputStream oin = new ObjectInputStream(fin);
			inventory = (ArrayList<ItemStack>) oin.readObject();
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		/*
		 * Set the Inventory items from read file
		 */
		System.out.println("Inventory size = "+ inventory.size());
		for(int i = 0; i < inventory.size(); i++){
			handler.getWorld().getInventory().getItemSlots().get(i).addItem(handler.getWorld().getInventory().getItemByID(inventory.get(i).getItem().getId()),
			inventory.get(i).getAmount());
			
		}
	}
	
	public static void loadEquipment(Handler handler){
		equipment = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/equipment.dat");
			ObjectInputStream oin = new ObjectInputStream(fin);
			equipment = (ArrayList<EquipmentStack>) oin.readObject();
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		/*
		 * Set the Inventory items from read file
		 */
		System.out.println("Equipment size = "+ equipment.size());
		for(int i = 0; i < equipment.size(); i++){
			
			handler.getWorld().getEquipment().getEquipmentSlots().get(equipment.get(i).getItem().getEquipSlot()).equipItem(handler.getWorld().getInventory().getItemByID(equipment.get(i).getItem().getId()));
			handler.getPlayer().addEquipmentStats(equipment.get(i).getItem().getEquipSlot());
		}
	}
	
	public static void addSaveData(String data){
		saveData.add(data);
	}
	
	public static void addInventoryItems(ItemStack itemStack){
		inventory.add(itemStack);
	}
	
	public static void addEquipmentItems(EquipmentStack equipmentStack){
		equipment.add(equipmentStack);
	}
	
	public static void clearSaveData(){
		saveData.clear();
	}
	
	public static void clearInventoryItems(){
		inventory.clear();
	}
	
	public static void clearEquipmentItems() {
		equipment.clear();
	}
	
	public static ArrayList<String> getSaveData() {
		return saveData;
	}
	
	public static ArrayList<ItemStack> getInventoryItems(){
		return inventory;
	}
	public static ArrayList<EquipmentStack> getEquipmentItems(){
		return equipment;
	}
	
}
