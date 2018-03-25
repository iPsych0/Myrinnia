package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Animation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int speed, index;
	private long lastTime, timer;
	private transient BufferedImage[] frames;
	
	public Animation(int speed, BufferedImage[] frames){
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void tick(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed){
			index++;
			timer = 0;
			if(index >= frames.length)
				index = 0;
		}
	}
	

	// TODO: Add function for a single animation cycle.
	
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}
	
	public BufferedImage getDefaultFrame(){
		return frames[1];
	}

}
