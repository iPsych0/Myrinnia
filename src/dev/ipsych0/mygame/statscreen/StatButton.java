package dev.ipsych0.mygame.statscreen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StatButton {
	
	BufferedImage buttonImage;
	String buttonText;
	
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
