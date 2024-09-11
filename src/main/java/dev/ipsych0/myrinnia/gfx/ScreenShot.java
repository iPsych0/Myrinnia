package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class ScreenShot {

    public static BufferedImage take() {
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(Handler.get().getGame().getDisplay().getWindowBounds());
        } catch (HeadlessException | AWTException e) {
            log.error("Could not take snapshot of screen.", e);
        }
        return image;
    }

}
