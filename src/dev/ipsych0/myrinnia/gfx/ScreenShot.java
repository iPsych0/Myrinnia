package dev.ipsych0.myrinnia.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenShot {

    public static BufferedImage take() {
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (HeadlessException | AWTException e) {
            e.printStackTrace();
        }
        return image;
    }

}
