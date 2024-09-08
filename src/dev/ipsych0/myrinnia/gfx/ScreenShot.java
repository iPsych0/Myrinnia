package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenShot {

    public static BufferedImage take() {
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(Handler.get().getGame().getDisplay().getWindowBounds());
        } catch (HeadlessException | AWTException e) {
            e.printStackTrace();
        }
        return image;
    }

}
