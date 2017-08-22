package dev.ipsych0.mygame.entities.creatures;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Creature.Direction;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;

public class Projectile extends Creature {
	
	private float x, y;
	private float xSpawn, ySpawn;
	private double xVelocity, yVelocity;
	private int maxX, maxY, minX, minY;
	private int mouseX, mouseY;
	private double angle;
	private int maxRadius = 256;
	
	public Projectile(Handler handler, float x, float y, int mouseX, int mouseY, float velocity) {
		super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		isNpc = false;
		attackable = false;
		
		if(mouseX > x) {
			setX(x + 32);
			System.out.println("true X>");
		}
		if(mouseY > y) {
			setY(y + 32);
			System.out.println("true Y>");
		}
		if(mouseX < x) {
			setX(x - 32);
			System.out.println("true X<");
		}
		if(mouseY < y) {
			setY(y - 32);
			System.out.println("true Y<");
		}
		
		speed = velocity;
		
		xSpawn = x;
		ySpawn = y;
		
		maxX = (int) (x + maxRadius);
		maxY = (int) (y + maxRadius);
		minX = (int) (x - maxRadius);
		minY = (int) (y - maxRadius);
		
		angle = Math.atan2(mouseY - y, mouseX - x);
		xVelocity = speed * Math.cos(angle);
		yVelocity = speed * Math.sin(angle);
		
		
	}
	
	public void tick() {
		if(x > maxX || x < minX || y > maxY || y < minY) {
			active = false;
		}
		xMove = (float) xVelocity;
		yMove = (float) yVelocity;
		move();
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.scorpion, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void die() {
		// IDK
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
	
	public void move(){
		if(!checkEntityCollisions(xMove, 0f)) {
			moveX();
		}else {
			active = false;
		}
		if(!checkEntityCollisions(0f, yMove)) {
			moveY();
		}else {
			active = false;
		}
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
				active = false;
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
				active = false;
			}
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
				active = false;
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
				active = false;
			}
		}
	}

}
