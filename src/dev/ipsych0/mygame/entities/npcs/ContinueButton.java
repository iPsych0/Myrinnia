package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;

public class ContinueButton {
	
	private int x, y, width, height;
	
	public ContinueButton(int x, int y) {
		this.x = 16;
		this.y = 728;
		width = 400;
		height = 20;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.chatwindowTop, x, y, width, height, null);
		Text.drawString(g, "Continue", x, y, true, Color.YELLOW, Assets.font14);
	}

}
