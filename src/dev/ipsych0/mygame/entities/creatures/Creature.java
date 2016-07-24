package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Font;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.tiles.Tiles;

public abstract class Creature extends Entity {

	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 32,
							DEFAULT_CREATURE_HEIGHT = 32;
	
	public static final int DEFAULT_ATTACK = 50;
	public static Font hpFont = new Font("SerifSans", Font.BOLD, 12);
//	public static Color hpColor = new Color(255, 120, 0);
	public static Color hpColor = new Color(140, 0, 255);
	protected int attackDamage;
	
	protected float speed;
	protected float xMove, yMove;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		attackDamage = DEFAULT_ATTACK;
		xMove = 0;
		yMove = 0;
	}

	public void move(){
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();
	}
	
	public void moveX(){
		if(xMove > 0){ // Moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tiles.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) && 
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)){
				x += xMove;
			} else{
				x = tx * Tiles.TILEWIDTH - bounds.x - bounds.width - 1;
			}
			
		}else if(xMove < 0){ // Moving left
			int tx = (int) (x + xMove + bounds.x) / Tiles.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) && 
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)){
				x += xMove;
			} else{
				x = tx * Tiles.TILEWIDTH + Tiles.TILEWIDTH - bounds.x;
			}
		}
	}
	
	public void moveY(){
		if(yMove < 0){ // Up
			int ty = (int) (y + yMove + bounds.y) / Tiles.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)){
				y += yMove;
			} else{
				y = ty * Tiles.TILEHEIGHT + Tiles.TILEHEIGHT - bounds.y;
			}
			
		}else if (yMove > 0){ // Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tiles.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)){
				y += yMove;
			} else{
				y = ty * Tiles.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x, int y){
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	// GETTERS + SETTERS
	
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
