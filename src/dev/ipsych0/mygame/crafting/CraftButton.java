package dev.ipsych0.mygame.crafting;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class CraftButton{
	
	private int x, y, width, height;
	private Rectangle bounds;
	private boolean hovering = false;
	private Handler handler;
	
	public CraftButton(Handler handler, int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		
		bounds = new Rectangle(x, y, width, height);
		
	}

	public void tick() {
		if(bounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()))
			hovering = true;
		else
			hovering = false;
	}

	public void render(Graphics g) {
		if(hovering)
			g.drawImage(Assets.mainMenuButton[0], x + 1, y + 1, CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE, null);
		else
			g.drawImage(Assets.mainMenuButton[1], x, y, CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE, null);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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

}
