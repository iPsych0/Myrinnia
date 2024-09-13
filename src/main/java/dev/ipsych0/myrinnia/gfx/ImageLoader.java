package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.SplashScreen;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            SplashScreen.addLoadedElement();

            InputStream input;

            input = new FileInputStream(path);

            BufferedImage img = ImageIO.read(input);
            img.setAccelerationPriority(1);
            input.close();
            return img;
        } catch (IOException e) {
            log.error("Could not load: %s".formatted(path), e);
            System.exit(1);
        }
        return null;
    }

}
