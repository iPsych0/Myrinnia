package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static String imageToString(BufferedImage image) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(DatatypeConverter.printBase64Binary(os.toByteArray()));
		return DatatypeConverter.printBase64Binary(os.toByteArray());
	}
	
	public static BufferedImage dataUrlToImage(String base64String) {     
	    byte[] bytes = DatatypeConverter.parseBase64Binary(base64String);

	    try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
	        return ImageIO.read(in);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
