package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CraftButton{
	
	private int x, y, width, height;
	private Rectangle bounds;
	
	public CraftButton(int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(x, y, width, height);
		
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y , CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE);
		g.setColor(Color.BLACK);
		g.drawRect(x, y , CraftingSlot.SLOTSIZE * 3, CraftingSlot.SLOTSIZE);
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
