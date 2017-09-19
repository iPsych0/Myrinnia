package dev.ipsych0.mygame.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.shop.ShopWindow;

public class DialogueButton {
	
	public int x, y, width, height;
	public String text;
	public Rectangle buttonBounds;
	private Handler handler;
	private String[] buttonParam = new String[2];
	
	public DialogueButton(Handler handler, int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.handler = handler;
		
		buttonBounds = new Rectangle(x, y, width, height);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.invSlot, x, y, width, height, null);
		Text.drawString(g, text, x + (width / 2), y + (height / 2), true, Color.YELLOW, Assets.font14);
	}
	
	public void pressedButton(String answer, String param){
		System.out.println("Chose answer: " + answer);
		
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

}
