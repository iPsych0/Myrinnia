package dev.ipsych0.mygame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.tiles.Tiles;

public abstract class Entity {

	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected int health;
	public static boolean isCloseToNPC = false;
	public static final int DEFAULT_HEALTH = 100;
	protected boolean active = true;
	protected boolean attackable = true;
	protected boolean isNpc = false;
	protected boolean isShop = false;
	protected boolean drawnOnMap = false;
	protected boolean damaged = false;
	protected boolean staticNpc = false;
	protected boolean shopping = false;
	private int ty = 0;
	
	
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
	
	public abstract void postRender(Graphics g);
	
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
	
	
	public boolean playerIsNearNpc(){
		// Looks for the closest entity and returns that entity
		if(distanceToEntity((int)handler.getPlayer().closestEntity().getX(), (int)handler.getPlayer().closestEntity().getY(), (int)handler.getWorld().getEntityManager().getPlayer().getX(), (int)handler.getWorld().getEntityManager().getPlayer().getY()) <= Tiles.TILEWIDTH * 2){
			// Interact with the respective speaking turn
			isCloseToNPC = true;
			return true;
		}else {
			// Out of range, so reset speaking turn
			isCloseToNPC = false;
			return false;
		}
					
	}
	
	
	/*
	 * Damage formula
	 */
	public void damage(int damageDealt){
		health -= damageDealt;
		damaged = true;
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		                damaged = false;
		                
		            }
		        }, 
		        480 
		);
		if(health <= 0){
			active = false;
			die();
		}
	}
	
	/*
	 * Drawing the hitsplat
	 */
	public void drawDamage(Graphics g) {
		if(damaged) {
			ty--;
			g.setColor(Color.YELLOW);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset() + 8), (int) (y - handler.getGameCamera().getyOffset() + 24 + ty), 20, 16);
			g.setColor(Color.RED);
			g.drawString("-" + Integer.toString(handler.getPlayer().damageFormula()),
					(int) (x - handler.getGameCamera().getxOffset() + 10), (int) (y - handler.getGameCamera().getyOffset() + 36 + ty));
			return;
		}
		ty = 0;
	}
	
	
	/*
	 * Check the distance to another entity
	 */
	public double distanceToEntity(int x1, int y1, int x2, int y2){
		int dx = x2 - x1;
	    int dy = y2 - y1;
	    return Math.sqrt(dx * dx + dy * dy);
	}
	
	// Getters & Setters
	
	/*
	 * Returns the collision bounds of an Entity
	 */
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

	public boolean isDrawnOnMap() {
		return drawnOnMap;
	}

	public void setDrawnOnMap(boolean drawnOnMap) {
		this.drawnOnMap = drawnOnMap;
	}

	public boolean isStaticNpc() {
		return staticNpc;
	}

	public void setStaticNpc(boolean staticNpc) {
		this.staticNpc = staticNpc;
	}
	
}
