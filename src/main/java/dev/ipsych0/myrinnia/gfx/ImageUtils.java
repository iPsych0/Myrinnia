package dev.ipsych0.myrinnia.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static final int HUE = 0;
    public static final int SATURATION = 1;
    public static final int VALUE = 2;

    public static void changeHsv(BufferedImage img, float percentage, int component) {
        // TODO: PERCENTAGE SHOULD BE (VALUE) / 360.0f!!!
        changePixels(img, percentage, component);
    }

    private static void changePixels(BufferedImage img, float percentage, int component) {
        int width = img.getWidth();
        int height = img.getHeight();
        for (int Y = 0; Y < height; Y++) {
            for (int X = 0; X < width; X++) {
                int RGB = img.getRGB(X, Y);
                int R = (RGB >> 16) & 0xff;
                int G = (RGB >> 8) & 0xff;
                int B = (RGB) & 0xff;
                float[] HSV = new float[3];
                Color.RGBtoHSB(R, G, B, HSV);
                img.setRGB(X, Y, getRBGfromHSV(HSV, percentage, component));
            }
        }
    }

    private static int getRBGfromHSV(float[] HSV, float percentage, int component) {
        switch (component) {
            case HUE:
                return Color.getHSBColor(percentage, HSV[1], HSV[2]).getRGB();
            case SATURATION:
                return Color.getHSBColor(HSV[0], percentage, HSV[2]).getRGB();
            case VALUE:
                return Color.getHSBColor(HSV[0], HSV[1], percentage).getRGB();
            default:
                return Color.getHSBColor(HSV[0], HSV[1], HSV[2]).getRGB();
        }
    }
}
