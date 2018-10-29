package dev.ipsych0.myrinnia.entities;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

public class HitSplat {
	
	private Entity receiver;
	private int damage;
	private boolean active = false;
	private int ty = 0;
	private int xOffset, yOffset;
	
	public HitSplat(Entity receiver, int damage) {
		this.receiver = receiver;
		this.damage = damage;
		active = true;
		xOffset = Handler.get().getRandomNumber(-16, 16);
		yOffset = Handler.get().getRandomNumber(-8, 8);
	}
	
	public void tick() {
		if(active) {
			
		}
	}
	
	public void render(Graphics g) {
		if(active) {
			ty++;
			Text.drawString(g, String.valueOf(damage),
					(int) (receiver.x - Handler.get().getGameCamera().getxOffset() + 6 + xOffset),
					(int) (receiver.y - Handler.get().getGameCamera().getyOffset() + 32 - ty + yOffset), false, Color.RED, Assets.font32);
			
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
