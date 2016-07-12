package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.ipsych0.mygame.Handler;

public class ItemManager {
	
	private Handler handler;
	private ArrayList<Item> items;

	public ItemManager(Handler handler){
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	
	public void tick(){
		Iterator<Item> it = items.iterator();
		while(it.hasNext()){
			Item i = it.next();
			if(handler.getKeyManager().pickUp){
				System.out.println("Current rendering item = " + i.getName());
				i.pickUpItem(Item.woodItem, 10);
			}
			// If item is picked up, remove from world and add to inventory, but for now remove it.
			if(i.getCount() == Item.PICKED_UP){
				// pickUp function to add to player's inventory, then remove from world with it.remove();
				it.remove();
			}
			i.tick();
		}
	}
	
	public void render(Graphics g){
		for(Item i : items)
			i.render(g);
	}
	
	public void addItem(Item i){
		i.setHandler(handler);
		items.add(i);
	}
	
	// Getters & Setters

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

}
