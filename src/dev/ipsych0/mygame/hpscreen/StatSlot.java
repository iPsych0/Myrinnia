package dev.ipsych0.mygame.hpscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.entities.npcs.NPCText;
import dev.ipsych0.mygame.gfx.Assets;

public class StatSlot {
	
	public static final int SLOTSIZE = 32;
	private int x, y;
	private StatButton statButton;
	int alpha = 127;
	Color interfaceColour = new Color(100, 100, 100, alpha);
	
	public StatSlot(int x, int y, StatButton statButton){
		this.x = x;
		this.y = y;
		this.statButton = statButton;
		
	}
	
	public void tick(){
		// hier iets van onClick voor buttons
	}
	
	public void render(Graphics g){
		g.setColor(interfaceColour);
		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);
		
		
		if(statButton != null){
			g.drawImage(getStatButton().getTexture(), x, y, StatSlot.SLOTSIZE, StatSlot.SLOTSIZE, null);
			g.setColor(Color.WHITE);
			g.drawString(getStatButton().getButtonText(), x + 3, y + 16);
		}
	}
	
	public void addStatSlot(BufferedImage texture, String message){
		this.statButton = new StatButton(texture, message);
	}

	public StatButton getStatButton() {
		return statButton;
	}

	public void setStatButton(StatButton statButton) {
		this.statButton = statButton;
	}

}
