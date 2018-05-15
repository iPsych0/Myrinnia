package dev.ipsych0.mygame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Projectile;

public class EntityManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private Player player;
	private CopyOnWriteArrayList<Entity> entities;
	private Collection<Entity> deleted;
	private Entity shoppingNpc;
	private Entity selectedEntity;
	public static boolean isPressed = false;
	private LinkedList<HitSplat> hitSplats;
	
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		entities = new CopyOnWriteArrayList<Entity>();
		deleted = new CopyOnWriteArrayList<Entity>();
		hitSplats = new LinkedList<>();
		addEntity(player);
	}
	
	public void tick(){
		/*
		 * Compares Entity A to Entity B
		 * @returns: -1 if the Y position is lower (render on top of Entity B)
		 * 			  1 if the Y position is higher (render behind Entity B)
		 */
		Comparator<Entity> renderSorter = new Comparator<Entity>(){
			@Override
			public int compare(Entity a, Entity b) {
				if(a.getY() + a.getHeight() < b.getY() + b.getHeight()){
					return -1;
				}else{
					return 1;
				}
			}
		};
		// Iterate over all Entities and remove inactive ones
		Iterator<Entity> it = entities.iterator();
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
			// Update combat timers
			if(e.isDamaged() && e.getDamageDealer() != null) {
				e.updateCombatTimer();
			}
		}
		
		// If enemies are dead, update the respawn timers
		if(deleted.size() > 0) {
			entities.removeAll(deleted);
			for(Entity e : deleted) {
				e.startRespawnTimer();
				if(e.getRespawnTimer() == 0) {
					e.respawn();
					deleted.remove(e);
				}
			}
		}
		entities.sort(renderSorter);
	}
	
	public void render(Graphics g){
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		for(Entity e : entities){
			
			if(e.getDamageReceiver() != null && handler.getPlayer().isInCombat()) {
				if(e.isAttackable())
					e.drawHP(g);
			}
			
			e.render(g);
			
			Rectangle entityRect = new Rectangle((int)e.getX() - (int)handler.getGameCamera().getxOffset(),
					(int)e.getY() - (int)handler.getGameCamera().getyOffset(), e.getWidth(), e.getHeight());
			
			if(e instanceof Creature) {
				for(Projectile p : ((Creature)e).getProjectiles()) {
					if(p.active) {
						p.render(g);
					}
				}
			}
			
			// If we rightclick an Entity, lock it to the top of the screen.
			if(entityRect.contains(mouse) && !e.equals(handler.getPlayer()) && handler.getMouseManager().isRightPressed() && !isPressed) {
				isPressed = true;
				if(e.isOverlayDrawn())
					selectedEntity = e;
			}
			
			// Keep rendering the selected Entity
			if(selectedEntity != null) {
				if(selectedEntity.active)
					selectedEntity.drawEntityOverlay(selectedEntity, g);
				else
					selectedEntity = null;
			}
			
			// If we clicked away, remove the locked UI component
			if(!entityRect.contains(mouse) && !handler.getChatWindow().getWindowBounds().contains(mouse) &&
					!handler.getCraftingUI().getWindowBounds().contains(mouse) &&
					!handler.getEquipment().getWindowBounds().contains(mouse) &&
					!handler.getInventory().getWindowBounds().contains(mouse) &&
					!e.equals(handler.getPlayer()) && handler.getMouseManager().isRightPressed() && !isPressed && selectedEntity != null) {
				isPressed = true;
				selectedEntity = null;
			}
			
			// If the mouse is hovered over an Entity, draw the overlay
			if(entityRect.contains(mouse) && !e.equals(handler.getPlayer())) {
				if(e.isOverlayDrawn())
					e.drawEntityOverlay(e, g);
			}
			
			e.postRender(g);
			
			if(shoppingNpc != null) {
				shoppingNpc.postRender(g);
			}
		}
		
		Collection<HitSplat> deleted = new CopyOnWriteArrayList<HitSplat>();
		if(hitSplats.size() != 0) {
			for(HitSplat hs : hitSplats) {
				if(hs.isActive()) {
					hs.render(g);
				}else {
					deleted.add(hs);
				}
			}
			hitSplats.removeAll(deleted);
		}
		
	}
	
	public void postRender(Graphics g) {
		// Post renders for entities for additional 
		player.postRender(g);
	}
	
	public void addEntity(Entity e){
		entities.add(e);
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

	public LinkedList<HitSplat> getHitSplats() {
		return hitSplats;
	}

	public void setHitSplats(LinkedList<HitSplat> hitSplats) {
		this.hitSplats = hitSplats;
	}

}
