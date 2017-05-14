package dev.ipsych0.mygame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.tiles.Tiles;

public abstract class Entity {

	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected int health;
	public static int speakingTurn;
	public static boolean isCloseToNPC = false;
	private ArrayList<Double> pythagoras;
	public static final int DEFAULT_HEALTH = 100;
	protected boolean active = true;
	protected boolean attackable = true;
	protected boolean isNpc = false;
	protected boolean isShop = false;
	protected boolean drawnOnMap = false;
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;
		speakingTurn = 0;
		pythagoras = new ArrayList<Double>();
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	// Abstract Methods
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public abstract void interact();
	
	// Collision checker
	
	public boolean checkEntityCollisions(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	
	// If player is within 64px of an NPC, return true, else return false.
	// TODO: Needs detection for multiple NPCs.
	public boolean playerIsNearNpc(Entity e){
			// TODO: ALWAYS CHECKS ALL NPCS, SO CHECK ALL DISTANCES AND ONLY RETURN THE CLOSEST ONE!!!!
			//System.out.println("Test 1: " + getSpeakingTurn());
			if(distanceToEntity((int)e.getX(), (int)e.getY(), (int)handler.getWorld().getEntityManager().getPlayer().getX(), (int)handler.getWorld().getEntityManager().getPlayer().getY()) <= Tiles.TILEWIDTH * 2){
				// Interact with the respective speaking turn
				if(getSpeakingTurn() == 0){
					e.interact();
					return true;
				}if(getSpeakingTurn() == 1){
					e.interact();
					return true;
				}if(getSpeakingTurn() == 2){
					e.interact();
					return true;
				}if(getSpeakingTurn() == 3){
					if(handler.getWorld().getEntityManager().getPlayer().getScorpionKC() >= 5){
						e.interact();
						return true;
					}
					else{
						speakingTurn -= 1;
						e.interact();
						return true;
					}
				}if(getSpeakingTurn() == 4){
					if(handler.getWorld().getEntityManager().getPlayer().getScorpionKC() < 5){
						speakingTurn -= 1;
						e.interact();
						return true;
					}else{
						e.interact();
						return true;
					}
				}
				if(getSpeakingTurn() >= 5){
					e.interact();
					speakingTurn = 5;
					return true;
				}
			}
			// Out of range, so reset speaking turn
			isCloseToNPC = false;
			speakingTurn = 0;
			return false;
			
					
	}
	
	// Damage function
	
	public void damage(int damageDealt){
		health -= damageDealt;
		if(health <= 0){
			active = false;
			die();
		}
	}
	
	public double distanceToEntity(int x1, int y1, int x2, int y2){
		int dx = x2 - x1;
	    int dy = y2 - y1;
	    return Math.sqrt(dx * dx + dy * dy);
	}
	
	//Checks distance for all entities, puts the distance in ascending order and returns the closest Entity
	public Entity closestEntity(int x1, int y1, int x2, int y2){
		System.out.println("Hoe vaak wordt dit geprint?");
		double closestDistance;
		Entity closestEntity = null;
		Hashtable<Double, Entity> hashTable = new Hashtable<Double, Entity>();
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(!e.isNpc()){
				continue;
			}
			if(e.equals(this)){
				continue;
			}
			int dx = x2 - x1;
		    int dy = y2 - y1;
		    hashTable.put(Math.sqrt(dx * dx + dy * dy), e);
		    pythagoras.add(Math.sqrt(dx * dx + dy * dy));
		    Collections.sort(pythagoras);
		}
		System.out.println("Closest NPC is " + pythagoras.get(0) + " pixels away!");
		closestDistance = pythagoras.get(0);
		pythagoras.removeAll(pythagoras);
		closestEntity = hashTable.get(closestDistance);
		System.out.println("closest Entity = " + hashTable.get(closestDistance));
		hashTable.clear();
		return closestEntity;
	}
	
	// Getters & Setters
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isAttackable(){
		return attackable;
	}

	public boolean isNpc() {
		return isNpc;
	}

	public void setNpc(boolean isNpc) {
		this.isNpc = isNpc;
	}

	public boolean isShop() {
		return isShop;
	}

	public void setShop(boolean isShop) {
		this.isShop = isShop;
	}

	public int getSpeakingTurn() {
		return speakingTurn;
	}

	public boolean isDrawnOnMap() {
		return drawnOnMap;
	}

	public void setDrawnOnMap(boolean drawnOnMap) {
		this.drawnOnMap = drawnOnMap;
	}
	
}
