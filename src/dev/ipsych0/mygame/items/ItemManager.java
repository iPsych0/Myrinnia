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
			// Checks player's position for any items nearby to pick up
			if(handler.getKeyManager().pickUp && handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(i.itemPosition(0, 0))){
				if(i.pickUpItem(i)){
					if(i.isPickedUp()){
						it.remove();
					}
					else{
					System.out.println("Picked_UP value is not set to -1.");
					}
				}
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
