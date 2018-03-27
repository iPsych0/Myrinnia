package dev.ipsych0.mygame.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import dev.ipsych0.mygame.Game;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.quests.QuestManager;
import dev.ipsych0.mygame.states.MenuState;
import dev.ipsych0.mygame.worlds.World;

public class SaveManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<String> variables;
	public static ArrayList<ItemStack> inventory;
	public static ArrayList<ItemStack> equipment;
	public static ArrayList<World> worlds;
	
	private static Handler handlerObject;
	private static QuestManager questManager;

	public SaveManager(Handler handler){
		handlerObject = handler;
		variables = new ArrayList<String>();
		inventory = new ArrayList<ItemStack>();
		equipment = new ArrayList<ItemStack>();
		worlds = new ArrayList<World>();
		questManager = handlerObject.getQuestManager();
	}

	public static void saveGame(){
		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream("res/savegames/save.txt"));
			for (int i = 0; i < variables.size(); i++) {
				try {
					// write down all the saved data stored in the Array
					output.writeUTF(variables.get(i));
					System.out.println("Saved data: " + variables.get(i));
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
			f = new FileOutputStream(new File("res/savegames/inventory.txt"));
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
	
	public static void saveQuests(){
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File("res/savegames/questmanager.txt"));
			ObjectOutputStream o;
			try {
				o = new ObjectOutputStream(f);
					o.writeObject(questManager);
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
			f = new FileOutputStream(new File("res/savegames/equipment.txt"));
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
	
	public static void savehandler(){
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File("res/savegames/handler.sav"));
			ObjectOutputStream o;
			try {
				MenuState.loadButtonPressed = false;
				handlerObject.getGame().getDisplay().getFrame().removeMouseListener(handlerObject.getMouseManager());
				handlerObject.getGame().getDisplay().getFrame().removeMouseMotionListener(handlerObject.getMouseManager());
				handlerObject.getGame().getDisplay().getCanvas().removeMouseListener(handlerObject.getMouseManager());
				handlerObject.getGame().getDisplay().getCanvas().removeMouseMotionListener(handlerObject.getMouseManager());
				o = new ObjectOutputStream(f);
					o.writeObject(handlerObject);
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
	
	public static void saveWorlds(){
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File("res/savegames/worlds.txt"));
			ObjectOutputStream o;
			try {
				o = new ObjectOutputStream(f);
					o.writeObject(worlds);
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
			DataInputStream input = new DataInputStream(new FileInputStream("res/savegames/save.txt"));
			try {
				while(input.available() > 0){
					variables.add(input.readUTF());
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
		
		// World loading
		handler.setWorld(handler.getWorldHandler().getWorlds().get(Integer.valueOf(variables.get(4))));
		
		// Player stuff
		handler.getWorld().getEntityManager().getPlayer().setAttackExperience(Integer.valueOf(variables.get(0)));
		handler.getWorld().getEntityManager().getPlayer().setX(Float.valueOf(variables.get(1)));
		handler.getWorld().getEntityManager().getPlayer().setY(Float.valueOf(variables.get(2)));
		handler.getWorld().getEntityManager().getPlayer().setHealth(Integer.valueOf(variables.get(3)));
		
	

	}
	
	public static void loadInventory(Handler handler){
		inventory = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/inventory.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);
			inventory = (ArrayList<ItemStack>) oin.readObject();
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		/*
		 * Set the Inventory items from read file
		 */
		System.out.println("Inventory size = "+ inventory.size());
		for(int i = 0; i < inventory.size(); i++){
			handler.getInventory().getItemSlots().get(handler.getInventory().findFreeSlot(inventory.get(i).getItem())).addItem(handler.getInventory().getItemByID(inventory.get(i).getItem().getId()),
			inventory.get(i).getAmount());
			
		}
	}
	
	public static void loadHandler(Handler handler){
		handlerObject = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/handler.sav");
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
		
		
		for(int i = 0; i < handler.getWorldHandler().getWorlds().size(); i++) {
			handler.getWorldHandler().getWorlds().get(i).setEntityManager(handlerObject.getWorldHandler().getWorlds().get(i).getEntityManager());
			handler.getWorldHandler().getWorlds().get(i).setItemManager(handlerObject.getWorldHandler().getWorlds().get(i).getItemManager());
		}
		
		handler.getGame().setKeyManager(handlerObject.getKeyManager());
		handler.getGame().setMouseManager(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getFrame().addMouseListener(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getFrame().addKeyListener(handlerObject.getKeyManager());
		handler.getGame().getDisplay().getFrame().addMouseMotionListener(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getCanvas().addMouseListener(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getCanvas().addMouseMotionListener(handlerObject.getMouseManager());
		handler.setPlayer(handlerObject.getPlayer());
		handler.getInventory().setItemSlots(handlerObject.getInventory().getItemSlots());
		handler.getEquipment().setEquipmentSlots(handlerObject.getEquipment().getEquipmentSlots());
		handler.setQuestManager(handlerObject.getQuestManager());
		handler.getGame().setGameCamera(handlerObject.getGameCamera());
//		handler.getGame().setHandler(handlerObject);
		
	}
	
	public static void loadQuests(Handler handler){
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/questmanager.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);
			handler.setQuestManager((QuestManager) oin.readObject());
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadEquipment(Handler handler){
		equipment = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/equipment.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);
			equipment = (ArrayList<ItemStack>) oin.readObject();
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		/*
		 * Set the Inventory items from read file
		 */
		System.out.println("Equipment size = "+ equipment.size());
		for(int i = 0; i < equipment.size(); i++){
			
			handler.getEquipment().getEquipmentSlots().get(equipment.get(i).getItem().getEquipSlot()).equipItem(handler.getInventory().getItemByID(equipment.get(i).getItem().getId()));
			handler.getPlayer().addEquipmentStats(equipment.get(i).getItem().getEquipSlot());
		}
	}
	
	public static void loadWorlds(Handler handler){
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/savegames/worlds.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);
			worlds = (ArrayList<World>) oin.readObject();
			oin.close();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		
		/*
		 * Set the Entity settings from file
		 */
		
		handler.getWorldHandler().setWorlds(worlds);
		
		handler.setWorld(handler.getWorldHandler().getWorlds().get(handler.getPlayer().getCurrentMap().getWorldID()));
		
//		for(int i = 0; i < handler.getWorldHandler().getWorlds().size(); i++) {
//			handler.getWorldHandler().getWorlds().get(i).setEntityManager(worlds.get(i));
//			for(int j = 0; j < handler.getWorldHandler().getWorlds().get(i).getEntityManager().getEntities().size(); j++){
//				handler.getWorldHandler().getWorlds().get(i).getEntityManager().getEntities().get(j).setHandler(handler);
//				handler.getWorldHandler().getWorlds().get(i).getEntityManager().setHandler(handler);	
//				if(handler.getWorld().getEntityManager().getEntities().get(j) instanceof Creature) {
//					((Creature)handler.getWorld().getEntityManager().getEntities().get(j)).setHandler(handler);
//					if(((Creature)handler.getWorld().getEntityManager().getEntities().get(j)).getMap() != null) {
//						((Creature)handler.getWorld().getEntityManager().getEntities().get(j)).getMap().setHandler(handler);
//					}
//				}
//			}
//		}
//		if(handler.getWorld().getEntityManager().getPlayer().getClosestEntity() != null)
//			if(handler.getWorld().getEntityManager().getPlayer().getClosestEntity().getChatDialogue() != null)
//				handler.getWorld().getEntityManager().getPlayer().getClosestEntity().getChatDialogue().setHandler(handler);

	}
	
	public static void addSaveData(String data){
		variables.add(data);
	}
	
	public static void addInventoryItems(ItemStack itemStack){
		inventory.add(itemStack);
	}
	
	public static void addEquipmentItems(ItemStack itemStack){
		equipment.add(itemStack);
	}
	
	public static void addEntityManagers(World world){
		worlds.add(world);
	}
	
	public static void clearSaveData(){
		variables.clear();
	}
	
	public static void clearInventoryItems(){
		inventory.clear();
	}
	
	public static void clearEquipmentItems() {
		equipment.clear();
	}
	
	public static void clearWorlds() {
		worlds.clear();
	}
	
	public static ArrayList<String> getSaveData() {
		return variables;
	}
	
	public static ArrayList<ItemStack> getInventoryItems(){
		return inventory;
	}
	
	public static ArrayList<ItemStack> getEquipmentItems(){
		return equipment;
	}
	
	public static ArrayList<World> getWorlds(){
		return worlds;
	}
	
}
