package dev.ipsych0.mygame.statscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;

public class StatButton {
	
	private BufferedImage buttonImage;
	private String buttonText;
	
	public StatButton(BufferedImage buttonImage, String buttonText){
		this.buttonImage = buttonImage;
		this.buttonText = buttonText;
		
	}
	
	public void tick(){

	}
	
	public void render(Graphics g){

	}

	public BufferedImage getButtonImage() {
		return buttonImage;
	}

	public void setButtonImage(BufferedImage buttonImage) {
		this.buttonImage = buttonImage;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

}
