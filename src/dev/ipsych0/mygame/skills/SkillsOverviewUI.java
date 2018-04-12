package dev.ipsych0.mygame.skills;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class SkillsOverviewUI {
	
	public int x = 192, y = 180, width = 400, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	
	public SkillsOverviewUI(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.shopWindow, x, y, width, height, null);
	}

}
