package dev.ipsych0.mygame.gfx;

import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.tiles.Tiles;

public class GameCamera implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float xOffset, yOffset;
	
	public GameCamera(float xOffset, float yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void checkBlankSpace(){
		if(xOffset < 0){
			xOffset = 0;
		} else if(xOffset > Handler.get().getWorld().getWidth() * Tiles.TILEWIDTH - Handler.get().getWidth()){
			xOffset = Handler.get().getWorld().getWidth() * Tiles.TILEWIDTH - Handler.get().getWidth();
		}
		
		if(yOffset < 0){
			yOffset = 0;
		} else if(yOffset > Handler.get().getWorld().getHeight() * Tiles.TILEHEIGHT - Handler.get().getHeight()){
			yOffset = Handler.get().getWorld().getHeight() * Tiles.TILEHEIGHT - Handler.get().getHeight();
		}
	}
	
	public void centerOnEntity(Entity e){
		xOffset = e.getX() - Handler.get().getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - Handler.get().getHeight() / 2 + e.getHeight() / 2;
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
