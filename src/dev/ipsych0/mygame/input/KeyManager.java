package dev.ipsych0.mygame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.statscreen.StatScreen;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.mapeditor.MiniMap;

public class KeyManager implements KeyListener{
	
	private UIManager uiManager;

	private boolean[] keys, justPressed, cantPress;
	private Handler handler;
	public boolean up, down, left, right;
	public boolean attack;
	public boolean chat;
	public boolean pickUp;
	public boolean position;
	public boolean talk;
	
	public KeyManager(){
		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];
	}
	
	public void setUIManager(UIManager uiManager){
		this.uiManager = uiManager;
	}
	
	public void tick(){
		
		for(int i = 0; i < keys.length; i++){
			if(cantPress[i] && !keys[i]){
				cantPress[i] = false;
			}else if(justPressed[i]){
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if(!cantPress[i] && keys[i]){
				justPressed[i] = true;
			}
		}
		
		if(keyJustPressed(KeyEvent.VK_E)){
			// Maybe hier optimaliseren van interfaces
		}
		
		// Movement keys
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		
		// Attack keys
		attack = keys[KeyEvent.VK_Z];

		
		// Interaction keys
		chat = keys[KeyEvent.VK_C];
		pickUp = keys[KeyEvent.VK_F];
		
		// Coordinate keys
		position = keys[KeyEvent.VK_P];
		talk = keys[KeyEvent.VK_SPACE];
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length){
			return;
		}
		keys[e.getKeyCode()] = true;
		
		// Inventory toggle
		if(e.getKeyCode() == KeyEvent.VK_I){
			if(!InventoryWindow.isOpen){
				InventoryWindow.isOpen = true;
			}
			else {
				InventoryWindow.isOpen = false;
			}
		}
		
		// Equipment toggle
		if(e.getKeyCode() == KeyEvent.VK_E){
			if(!EquipmentWindow.isOpen){
				EquipmentWindow.isOpen = true;
			}
			else {
				EquipmentWindow.isOpen = false;
			}
		}
		
		// Chat window toggle
		if(e.getKeyCode() == KeyEvent.VK_C){
			if(!ChatWindow.chatIsOpen){
				ChatWindow.chatIsOpen = true;
			}
			else {
				ChatWindow.chatIsOpen = false;
			}
		}
		
		// Chat window toggle
		if(e.getKeyCode() == KeyEvent.VK_M){
			if(!MiniMap.isOpen){
				MiniMap.isOpen = true;
			}
			else {
				MiniMap.isOpen = false;
			}
		}
		
//		if(e.getKeyCode() == KeyEvent.VK_H){
//			if(!StatScreen.isOpen){
//				StatScreen.isOpen = true;
//			}
//			else {
//				StatScreen.isOpen = false;
//			}
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length){
			return;
		}
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_SPACE && ChatWindow.chatIsOpen && Entity.isCloseToNPC){
			Player.hasInteracted = false;
		}
	}
	
	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length){
			return false;
		}
		return justPressed[keyCode];
	}

}
