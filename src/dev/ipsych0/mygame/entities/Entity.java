package dev.ipsych0.mygame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.tiles.Tiles;

public abstract class Entity {

	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected int health;
	protected boolean isTalking = false;
	public static final int DEFAULT_HEALTH = 100;
	protected boolean active = true;
	protected boolean attackable = true;
	protected boolean isNpc = false;
	protected boolean isShop = false;
	
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;
		
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
	// TODO: Needs detection for multiple NPCs. If there are two NPCs, distance is <= 32 for one NPC, but not for the other so chatbox closes
	public boolean playerIsNearNpc(){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this)){
				continue;
			}
			if(!e.isNpc){
				continue;
			}
			if(ChatWindow.chatIsOpen){
				if(distanceToEntity((int)e.getX(), (int)e.getY(),
						(int)handler.getWorld().getEntityManager().getPlayer().getX(), (int)handler.getWorld().getEntityManager().getPlayer().getY())
						<= (Tiles.TILEWIDTH * 2)){
					// Probleem zit hier -v continu de functie interact
					e.interact();
					System.out.println("Interacting");
					return true;
				}
				ChatWindow.chatIsOpen = false;
			}
			return false;
		}
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
	
}
