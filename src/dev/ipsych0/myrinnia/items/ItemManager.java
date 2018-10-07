package dev.ipsych0.myrinnia.items;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.myrinnia.Handler;

public class ItemManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1092891818645452920L;
	private CopyOnWriteArrayList<Item> items;
	private Collection<Item> deleted;
	private Collection<Item> added;

	public ItemManager(){
		items = new CopyOnWriteArrayList<Item>();
		deleted = new CopyOnWriteArrayList<Item>();
		added = new CopyOnWriteArrayList<Item>();
	}
	
	public void tick(){
		Iterator<Item> it = items.iterator();
		while(it.hasNext()){
			Item i = it.next();
			
			// Checks player's position for any items nearby to pick up
			if(Handler.get().getMouseManager().isRightPressed() && Handler.get().getWorld().getEntityManager().getPlayer().itemPickupRadius().intersects(i.itemPosition(0, 0))){
				if(!Handler.get().getPlayer().hasRightClickedUI(Handler.get().getMouse())) {
					if(i.pickUpItem(i)){
						if(i.isPickedUp()){
							deleted.add(i);
						}
					}
				}
			}
			i.tick();
		}
		
		// If non-worldspawn Items are dropped, start timer for despawning
		if(added.size() > 0) {
			for(Item i : added) {
				i.startRespawnTimer();
				
				// If item is picked up, reset the timer
				if(i.isPickedUp()) {
					i.setRespawnTimer(10800);
					deleted.add(i);
					added.remove(i);
				}
				// If the timer expires, remove the item
				else if(i.getRespawnTimer() == 0) {
					deleted.add(i);
					added.remove(i);
				}
			}
		}
		
		// If Item's timer is 0, remove the items from the world.
		if(deleted.size() > 0) {
			items.removeAll(deleted);
		}
	}
	
	public void render(Graphics g){
		for(Item i : items) {
			i.render(g);
			
			// Draw item bounds for picking up
//			g.drawRect((int)(i.itemPosition(0, 0).x - Handler.get().getGameCamera().getxOffset()), (int)(i.itemPosition(0, 0).y - Handler.get().getGameCamera().getyOffset()), i.itemPosition(0, 0).width, i.itemPosition(0, 0).height);
		}
	}
	
	public void addItem(Item i){
		items.add(i);
		added.add(i);
	}
	
	public void addItem(Item i, boolean isWorldSpawn){
		items.add(i);
	}
	
	// Getters & Setters
	
	public CopyOnWriteArrayList<Item> getItems() {
		return items;
	}

	public void setItems(CopyOnWriteArrayList<Item> items) {
		this.items = items;
	}

}
