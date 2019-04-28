package dev.ipsych0.myrinnia.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            InputStream is = ImageLoader.class.getResourceAsStream(path);
            BufferedImage img = ImageIO.read(is);
            is.close();
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load: " + path);
            System.exit(1);
        }
        return null;
    }

}
