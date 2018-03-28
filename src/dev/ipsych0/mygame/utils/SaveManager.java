package dev.ipsych0.mygame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import dev.ipsych0.mygame.Handler;


public class SaveManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Handler handlerObject;

	public SaveManager(Handler handler){
		handlerObject = handler;
	}
	public static void savehandler(){
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File("res/savegames/handler.sav"));
			ObjectOutputStream o;
			try {
//				MenuState.loadButtonPressed = false;
//				handlerObject.getGame().getDisplay().getFrame().removeMouseListener(handlerObject.getMouseManager());
//				handlerObject.getGame().getDisplay().getFrame().removeKeyListener(handlerObject.getKeyManager());
//				handlerObject.getGame().getDisplay().getFrame().removeMouseMotionListener(handlerObject.getMouseManager());
//				handlerObject.getGame().getDisplay().getCanvas().removeMouseListener(handlerObject.getMouseManager());
//				handlerObject.getGame().getDisplay().getCanvas().removeMouseMotionListener(handlerObject.getMouseManager());
				handlerObject.getGame().getMouseManager().setLeftPressed(false);
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
		
		handler.getGame().setHandler(handlerObject);
		handler.getGame().setKeyManager(handlerObject.getKeyManager());
		handler.getGame().setMouseManager(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getFrame().addMouseListener(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getFrame().addKeyListener(handlerObject.getKeyManager());
		handler.getGame().getDisplay().getFrame().addMouseMotionListener(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getCanvas().addMouseListener(handlerObject.getMouseManager());
		handler.getGame().getDisplay().getCanvas().addMouseMotionListener(handlerObject.getMouseManager());
		
		handler.getWorldHandler().getWorlds().clear();
		for(int i = 0; i < handlerObject.getWorldHandler().getWorlds().size(); i++) {
			handler.getWorldHandler().getWorlds().add(handlerObject.getWorldHandler().getWorlds().get(i));
//			handler.getWorldHandler().getWorlds().get(i).setEntityManager(handlerObject.getWorldHandler().getWorlds().get(i).getEntityManager());
//			handler.getWorldHandler().getWorlds().get(i).setItemManager(handlerObject.getWorldHandler().getWorlds().get(i).getItemManager());
		}
		
		handler.setChatWindow(handlerObject.getChatWindow());
		handler.setCraftingUI(handlerObject.getCraftingUI());
		handler.getInventory().setItemSlots(handlerObject.getInventory().getItemSlots());
		handler.getEquipment().setEquipmentSlots(handlerObject.getEquipment().getEquipmentSlots());
		handler.setQuestManager(handlerObject.getQuestManager());
		handler.getGame().setGameCamera(handlerObject.getGameCamera());
//		handler.getGame().setHandler(handlerObject);
		
	}
}
