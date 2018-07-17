package dev.ipsych0.mygame.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.gfx.Assets;

public class DialogueButton implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y, width, height;
	private String text;
	private Rectangle buttonBounds;
	private String[] buttonParam = new String[2];
	private boolean hovering = false;
	
	public DialogueButton(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		
		buttonBounds = new Rectangle(x, y, width, height);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(hovering)
			g.drawImage(Assets.genericButton[0], x, y, width, height, null);
		else
			g.drawImage(Assets.genericButton[1], x, y, width, height, null);
		Text.drawString(g, text, x + (width / 2), y + (height / 2), true, Color.YELLOW, Assets.font14);
	}
	
	public void pressedButton(String answer, String param){
		buttonParam[0] = answer;
		buttonParam[1] = param;
	}

	public Rectangle getButtonBounds() {
		return buttonBounds;
	}

	public void setButtonBounds(Rectangle buttonBounds) {
		this.buttonBounds = buttonBounds;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getButtonParam() {
		return buttonParam;
	}

	public void setButtonParam(String[] buttonParam) {
		this.buttonParam = buttonParam;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

}
