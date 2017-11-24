package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;

public class ChatOptions {
	
	private Handler handler;
	private int x, y, width, height;
	private String message;
	private boolean isHovering = false;
	
	public ChatOptions(Handler handler, int x, int y, String message) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.message = message;
		
		width = 400;
		height = 20;
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(isHovering) {
			g.drawImage(Assets.mainMenuButton[0], x + 1, y, width, height, null);
		}else {
			g.drawImage(Assets.mainMenuButton[1], x + 1, y, width, height, null);
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

}
