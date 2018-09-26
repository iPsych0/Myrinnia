package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class ChatOptions implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7933908780433920065L;
	private int x, y, width, height;
	private String message;
	private boolean isHovering = false;
	private int optionID;
	private Rectangle bounds;
	
	public ChatOptions(int x, int y, int optionID, String message) {
		this.x = x;
		this.y = y;
		this.optionID = optionID;
		this.message = message;
		
		width = 400;
		height = 20;
		
		bounds = new Rectangle(x, y, width, height);
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(isHovering) {
			g.drawImage(Assets.genericButton[0], x + 2, y + 1, width, height, null);
		}else {
			g.drawImage(Assets.genericButton[1], x + 1, y, width, height, null);
		}
		Text.drawString(g, message, x + (width / 2), y + 11, true, Color.YELLOW, Assets.font14);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHovering() {
		return isHovering;
	}

	public void setHovering(boolean isHovering) {
		this.isHovering = isHovering;
	}

	public int getOptionID() {
		return optionID;
	}

	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
