package dev.ipsych0.mygame.entities;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class HitSplat {
	
	private Handler handler;
	private Entity receiver;
	private int damage;
	private boolean active = false;
	private int ty = 0;
	private int xOffset, yOffset;
	
	public HitSplat(Handler handler, Entity receiver, int damage) {
		this.handler = handler;
		this.receiver = receiver;
		this.damage = damage;
		active = true;
		xOffset = handler.getRandomNumber(-16, 16);
		yOffset = handler.getRandomNumber(-8, 8);
	}
	
	public void tick() {
		if(active) {
			
		}
	}
	
	public void render(Graphics g) {
		if(active) {
			ty++;
			Text.drawString(g, String.valueOf(damage),
					(int) (receiver.x - handler.getGameCamera().getxOffset() + 6 + xOffset),
					(int) (receiver.y - handler.getGameCamera().getyOffset() + 32 - ty + yOffset), false, Color.RED, Assets.font32);
			
			if(ty >= 45) {
				active = false;
			}
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
