package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.states.GameState;

public class TextSlot {
	
	public static final int textWidth = 432;
	public static final int textHeight = 12;
	
	private int x, y;
	private NPCText npcText;
	int alpha = 16;
	Color interfaceColour = new Color(160, 160, 160, alpha);
	
	public TextSlot(int x, int y, NPCText npcText){
		this.x = x;
		this.y = y;
		this.npcText = npcText;
	}
	
	public void tick(){

	}
	
	public void render(Graphics g){
		g.setColor(interfaceColour);
		g.fillRect(x - 228, y + 184, textWidth, textHeight);
		
		g.setColor(Color.BLACK);
		g.drawRect(x - 228, y + 184, textWidth, textHeight);
		
		g.setFont(GameState.myFont);
		g.setColor(Color.WHITE);
		
		if(npcText != null){
			g.drawString(npcText.getLine(), x - 227, y + 195);
		}
		// TODO: Add render for npcTexts here
	}
	
	public void addTextSlot(String message){
		this.npcText = new NPCText(message);
	}

	public NPCText getNpcText() {
		return npcText;
	}

	public void setNpcText(NPCText npcText) {
		this.npcText = npcText;
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
	
	

}
