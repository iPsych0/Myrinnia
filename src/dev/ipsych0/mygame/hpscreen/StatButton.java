package dev.ipsych0.mygame.hpscreen;

import java.awt.image.BufferedImage;

public class StatButton {
	
	private String buttonText;
	private BufferedImage texture;
	
	public StatButton(BufferedImage texture, String buttonText){
		this.texture = texture;
		this.buttonText = buttonText;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

}
