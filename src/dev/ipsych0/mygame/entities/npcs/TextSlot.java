package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.states.GameState;

public class TextSlot {
	
	public static final int textWidth = 432;
	public static final int textHeight = 13;
	
	private int x, y;
	private NPCText npcText;
	int alpha = 127;
	Color interfaceColour = new Color(100, 100, 100, alpha);
	
	public TextSlot(int x, int y, NPCText npcText){
		this.x = x;
		this.y = y;
		this.npcText = npcText;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
//		g.setColor(interfaceColour);
//		g.fillRect(x, y + 17, textWidth, textHeight);
//		
//		g.setColor(Color.BLACK);
//		g.drawRect(x, y + 17, textWidth, textHeight);
		
		g.setFont(GameState.myFont);
		g.setColor(Color.YELLOW);
		
		if(npcText != null){
			Text.drawString(g, npcText.getLine(), x + 5, y + 28, false, Color.YELLOW, Assets.font14);
			//g.drawString(npcText.getLine(), x + 5, y + 28);
		}
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
