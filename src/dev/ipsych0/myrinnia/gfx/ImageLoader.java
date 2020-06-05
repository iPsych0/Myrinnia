package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.utils.FileUtils;
import dev.ipsych0.splashscreen.SplashScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            SplashScreen.addLoadedElement();

            InputStream input;

            String fixedFile = FileUtils.getResourcePath(path);
            input = new FileInputStream(fixedFile);

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
