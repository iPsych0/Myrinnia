package dev.ipsych0.mygame.gfx;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ScreenShot {
	
	public static BufferedImage take() {
		BufferedImage image = null;
		try {
			image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		} catch (HeadlessException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

}
