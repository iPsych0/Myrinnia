package dev.ipsych0.myrinnia.recap;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class RecapEvent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6372863712369781038L;
	private transient BufferedImage img;
	private String description;
	
	public RecapEvent(BufferedImage img, String description) {
		this.img = img;
		this.description = description;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
	}

	public BufferedImage getImg() {
		return img;
	}

	public String getDescription() {
		return description;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
	    out.defaultWriteObject();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(this.img, "png", buffer);

        out.writeInt(buffer.size()); // Prepend image with byte count
        buffer.writeTo(out);         // Write image
        buffer.close();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    in.defaultReadObject();

        int size = in.readInt(); // Read byte count
        byte[] buffer = new byte[size];
        in.readFully(buffer); // Make sure you read all bytes of the image

        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        this.img = ImageIO.read(is);
        is.close();
    }

}
