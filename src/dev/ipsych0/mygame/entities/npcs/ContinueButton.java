package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class ContinueButton implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y, width, height;
	private boolean isHovering = false;
	private boolean isPressed = false;
	
	public ContinueButton(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 100;
		this.height = 20;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(isHovering) {
			g.drawImage(Assets.genericButton[0], x, y, width, height, null);
		}else {
			g.drawImage(Assets.genericButton[1], x, y, width, height, null);
		}
		Text.drawString(g, "Continue", x + (width / 2), y + 11, true, Color.YELLOW, Assets.font14);
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

	public boolean isHovering() {
		return isHovering;
	}

	public void setHovering(boolean isHovering) {
		this.isHovering = isHovering;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

}
