package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;

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
	
	private void writeObject(ObjectOutputStream out) throws IOException {
	    out.defaultWriteObject();
	    out.writeInt(frames.length); // how many images are serialized?

	    for (BufferedImage eachImage : frames) {
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        ImageIO.write(eachImage, "png", buffer);

	        out.writeInt(buffer.size()); // Prepend image with byte count
	        buffer.writeTo(out);         // Write image
	    }
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    in.defaultReadObject();

	    int imageCount = in.readInt();
	    this.frames = new BufferedImage[imageCount];
	    for (int i = 0; i < imageCount; i++) {
	        int size = in.readInt(); // Read byte count

	        byte[] buffer = new byte[size];
	        in.readFully(buffer); // Make sure you read all bytes of the image

	        this.frames[i] = ImageIO.read(new ByteArrayInputStream(buffer));
	    }
	}

}
