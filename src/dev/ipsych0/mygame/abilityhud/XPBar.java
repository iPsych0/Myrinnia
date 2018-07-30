package dev.ipsych0.mygame.abilityhud;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.utils.Text;

public class XPBar {
	
	private Handler handler;
	private int x, y, width, height;

	public XPBar(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = 32;
		this.height = 64;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y - 32, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y - 32, width, height);
		Text.drawString(g, "XP", x + width / 2, y + height / 2 - 32, true, Color.BLACK, Assets.font14);
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
