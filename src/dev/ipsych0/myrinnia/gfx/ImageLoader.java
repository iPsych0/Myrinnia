package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            InputStream input;

            String fixedFile;
            if (Handler.isJar) {
                fixedFile = Handler.jarFile.getParentFile().getAbsolutePath() + path;
                input = new FileInputStream(fixedFile);
            } else {
                fixedFile = path.replaceFirst("/", Handler.resourcePath);
                input = new FileInputStream(fixedFile);
            }

            BufferedImage img = ImageIO.read(input);
            img.setAccelerationPriority(1);
            input.close();
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load: " + path);
            System.exit(1);
        }
        return null;
    }

}
