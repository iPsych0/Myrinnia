package dev.ipsych0.mygame.abilityhud;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.hpoverlay.HPOverlay;
import dev.ipsych0.mygame.utils.Text;

public class HPBar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private int x, y, width, height;

	public HPBar(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y - 32;
		this.width = 32;
		this.height = 64;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		// HP Bar
		g.drawImage(Assets.invScreen, x, y, width, height, null);
		g.setColor(HPOverlay.hpColorRed);
		g.fillRoundRect(x + 1, y + 1, width - 3, height - 3, 2, 4);
		g.setColor(HPOverlay.hpColorRedOutline);
		g.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 2, 4);
		
		g.setColor(HPOverlay.hpColorGreen);
		if(handler.getPlayer().getHealth() >= handler.getPlayer().getMaxHealth()) {
			g.fillRoundRect( x + 1,  y + 1,  width - 3,  height - 3, 2, 4);
			
			g.setColor(HPOverlay.hpColorGreenOutline);
			g.drawRoundRect( x + 1,  y + 1,  width - 3,  height - 3, 2, 4);
		}else {
			double yOffset = (double)handler.getPlayer().getHealth() /
					(double)handler.getPlayer().getMaxHealth();
			g.fillRoundRect( x + 1,  y + 1 +(int)(height * (1-yOffset)), width - 3, height - 3, 2, 4);
			
			g.setColor(HPOverlay.hpColorGreenOutline);
			g.drawRoundRect( x + 1,  y + 1 +(int)(height * (1-yOffset)), width - 3, height - 3, 2, 4);
		}
		Text.drawString(g, "HP", x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
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
