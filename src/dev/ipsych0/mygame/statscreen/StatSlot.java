package dev.ipsych0.mygame.statscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StatSlot {
	
	private int x, y;
	private int width, height;
	private StatButton statButton;
	
	public StatSlot(int x, int y, int width, int height, StatButton statButton){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.statButton = statButton;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(statButton != null){
			g.drawImage(statButton.getButtonImage(), x, y, width, height, null);
			g.setColor(Color.WHITE);
			g.drawRect(x, y, width, height);
			g.drawString(statButton.getButtonText(), x + 22, y + 20);
		}
	}
	
	public void addButton(BufferedImage buttonImage, String buttonText){
		this.statButton = new StatButton(buttonImage, buttonText);
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

	public StatButton getStatButton() {
		return statButton;
	}

	public void setStatButton(StatButton statButton) {
		this.statButton = statButton;
	}

}
