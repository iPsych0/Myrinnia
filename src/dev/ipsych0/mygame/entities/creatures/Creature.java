package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Font;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.tiles.Tiles;

public abstract class Creature extends Entity {

	public static final float DEFAULT_SPEED = 1.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 32,
							DEFAULT_CREATURE_HEIGHT = 32;
	
	public static final int DEFAULT_ATTACK = 50;
	public static Font hpFont = new Font("SansSerif", Font.BOLD, 12);
	public static Color hpColor = new Color(255, 255, 48);
	protected int attackDamage;
	
	protected enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	protected Direction direction;
	
	// Last faced direction
	private Direction lastFaced;
	
	protected float speed;
	protected float xMove, yMove;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		attackDamage = DEFAULT_ATTACK;
		xMove = 0;
		yMove = 0;
		drawnOnMap = true;
	}

	public void move(){
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();
	}
	
	public void moveX(){
		if(xMove > 0){ // Moving right
			direction = Direction.RIGHT;
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tiles.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) && 
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT) &&
					!collisionWithTerrain(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
					!collisionWithTerrain(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT) &&
					!collisionWithAmbiance(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
					!collisionWithAmbiance(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)){
				x += xMove;
			} else{
				x = tx * Tiles.TILEWIDTH - bounds.x - bounds.width - 1;
			}
			
		}else if(xMove < 0){ // Moving left
			direction = Direction.LEFT;
			int tx = (int) (x + xMove + bounds.x) / Tiles.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
					!collisionWithTerrain(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
					!collisionWithAmbiance(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT) &&
					!collisionWithTerrain(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT) &&
					!collisionWithAmbiance(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)){
				x += xMove;
			} else{
				x = tx * Tiles.TILEWIDTH + Tiles.TILEWIDTH - bounds.x;
			}
		}
		else if(xMove == 0){
			direction = lastFaced;
		}
	}
	
	
	public void moveY(){
		if(yMove < 0){ // Up
			direction = Direction.UP;
			int ty = (int) (y + yMove + bounds.y) / Tiles.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTerrain((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithAmbiance((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTerrain((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty) &&
					!collisionWithAmbiance((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)){
				y += yMove;
			} else{
				y = ty * Tiles.TILEHEIGHT + Tiles.TILEHEIGHT - bounds.y;
			}
			
		}else if (yMove > 0){ // Down
			direction = Direction.DOWN;
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tiles.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTerrain((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithAmbiance((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTerrain((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty) &&
					!collisionWithAmbiance((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)){
				y += yMove;
			} else{
				y = ty * Tiles.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
		else if(yMove == 0){
			direction = lastFaced;
		}
	}
	
	protected boolean collisionWithTile(int x, int y){
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	protected boolean collisionWithTerrain(int x, int y){
		return handler.getWorld().getTerrain(x, y).isSolid();
	}
	
	protected boolean collisionWithAmbiance(int x, int y){
		return handler.getWorld().getAmbiance(x, y).isSolid();
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

	@Override
	public int getHealth() {
		return health;
	}

	@Override
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
