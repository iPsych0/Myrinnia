package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.utils.Text;

public class TextSlot implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int textWidth = 432;
	public static final int textHeight = 15;
	
	private int x, y;
	private String message;
	
	public TextSlot(int x, int y, String message){
		this.x = x;
		this.y = y;
		this.message = message;
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
		
		if(message != null){
			Text.drawString(g, message, x + 6, y + 24, false, Color.YELLOW, Assets.font14);
			//g.drawString(npcText.getLine(), x + 5, y + 28);
		}
	}
	
	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
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
