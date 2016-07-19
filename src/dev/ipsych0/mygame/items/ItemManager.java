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
			if(handler.getKeyManager().pickUp && handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(i.itemPosition(0, 0))){
				// HARDCODED WOODITEM, DOESN'T PICK UP ROCK ITEMS, BECAUSE IT'S HARDCODED.
				i.pickUpItem(i, 10);
				if(i.getCount() == Item.PICKED_UP){
					for(int j = 0; j < getItems().size(); j++){
						System.out.println("item list before removal: " + getItems().get(j).getName());
					}
					it.remove();
					for(int j = 0; j < getItems().size(); j++){
						System.out.println("item list after removal: " + getItems().get(j).getName());
					}
				}
				else{
				System.out.println("Picked_Up value is not set to -1.");
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
