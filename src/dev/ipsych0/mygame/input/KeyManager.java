package dev.ipsych0.mygame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.ipsych0.mygame.items.InventoryWindow;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	public boolean up, down, left, right;
	public boolean aUp, aDown, aLeft, aRight;
	public boolean interact;
	public boolean pickUp;
	
	public KeyManager(){
		keys = new boolean[256];
	}
	
	public void tick(){
		// Movement keys
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		
		// Attack keys
		aUp = keys[KeyEvent.VK_W];
		aDown = keys[KeyEvent.VK_S];
		aLeft = keys[KeyEvent.VK_A];
		aRight = keys[KeyEvent.VK_D];
		
		// Interaction keys
		interact = keys[KeyEvent.VK_SPACE];
		pickUp = keys[KeyEvent.VK_F];
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
