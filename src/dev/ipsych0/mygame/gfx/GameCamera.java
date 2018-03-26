package dev.ipsych0.mygame.gfx;

import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.tiles.Tiles;

public class GameCamera implements Serializable{
	
	private transient Handler handler;
	private float xOffset, yOffset;
	
	public GameCamera(Handler handler, float xOffset, float yOffset){
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void checkBlankSpace(){
		if(xOffset < 0){
			xOffset = 0;
		} else if(xOffset > handler.getWorld().getWidth() * Tiles.TILEWIDTH - handler.getWidth()){
			xOffset = handler.getWorld().getWidth() * Tiles.TILEWIDTH - handler.getWidth();
		}
		
		if(yOffset < 0){
			yOffset = 0;
		} else if(yOffset > handler.getWorld().getHeight() * Tiles.TILEHEIGHT - handler.getHeight()){
			yOffset = handler.getWorld().getHeight() * Tiles.TILEHEIGHT - handler.getHeight();
		}
	}
	
	public void centerOnEntity(Entity e){
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}
	
	public void move(float xAmount, float yAmount){
		xOffset += xAmount;
		yOffset += yAmount;
		checkBlankSpace();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

}
