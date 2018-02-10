package dev.ipsych0.mygame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.entities.creatures.Player;

public class EntityManager {
	
	private Handler handler;
	private Player player;
	private CopyOnWriteArrayList<Entity> entities;
	private Entity shoppingNpc;
	
	/*
	 * Compares Entity A to Entity B
	 * @returns: -1 if the Y position is lower (render on top of Entity B)
	 * 			  1 if the Y position is higher (render behind Entity B)
	 */
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight()){
				return -1;
			}else{
				return 1;
			}
		}
	};
	
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		entities = new CopyOnWriteArrayList<Entity>();
		addEntity(player);
	}
	
	public void tick(){
		// Iterate over all Entities and remove inactive ones
		Iterator<Entity> it = entities.iterator();
		Collection<Entity> deleted = new CopyOnWriteArrayList<Entity>();
		while(it.hasNext()){
			Entity e = it.next();
			if(!e.isActive()){
				deleted.add(e);
			}
			//If the player is shopping with this Entity, set the shopping Entity to this one.
			if(e.shopping) {
				shoppingNpc = e;
			}
			e.tick();
		}
		entities.removeAll(deleted);
		entities.sort(renderSorter);
	}
	
	public void render(Graphics g){
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		for(Entity e : entities){
			e.render(g);
			
			Rectangle entityRect = new Rectangle((int)e.getX() - (int)handler.getGameCamera().getxOffset(),
					(int)e.getY() - (int)handler.getGameCamera().getyOffset(), e.getWidth(), e.getHeight());
			
			// If the mouse is hovered over an Entity, draw the overlay
			if(entityRect.contains(mouse) && !e.equals(handler.getPlayer())) {
				if(e.isOverlayDrawn())
					e.drawEntityOverlay(e, g);
			}
			
			// Draw the damage while in combat
			if(e.isDamaged() && e.getDamageDealer() != null) {
				e.drawDamage(e.getDamageDealer(), g);
			}
		}
		
		// If we're shopping, render the shopping screen on top
		if(shoppingNpc != null)
			shoppingNpc.postRender(g);
	}
	
	public void postRender(Graphics g) {
		// Post renders for entities for additional 
		player.postRender(g);
	}
	
	public void addEntity(Entity e){
		entities.add(e);
//		ListIterator<Entity> it = entities.listIterator();
//		it.add(e);
//		it.previous();
	}

	// Getters & Setters
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public CopyOnWriteArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(CopyOnWriteArrayList<Entity> entities) {
		this.entities = entities;
	}

}
