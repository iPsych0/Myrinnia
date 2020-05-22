package dev.ipsych0.myrinnia.worlds.weather;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Fog implements Weather, Serializable {

    private static final int WIDTH = Handler.get().getWidth(), HEIGHT = Handler.get().getHeight();
    private static final int FOG_W = 128, FOG_H = 32;
    private static final long serialVersionUID = 5790736826993930044L;
    private int xOffset;

    // Shading effect
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 1.0f};
    private static final Color[] colors = {new Color(139, 170, 184, 53), new Color(167, 233, 243, 107)};
    private static final RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, colors);
    private Intensity intensity;

    public enum Intensity {
        NORMAL, HEAVY
    }

    public Fog() {
        this.intensity = Intensity.NORMAL;
    }

    public Fog(Intensity intensity) {
        this.intensity = intensity;
    }

    @Override
    public void tick() {
        xOffset += 1;

        if (Handler.get().getPlayer().getxMove() > 0) {
            xOffset += Handler.get().getPlayer().getSpeed();
        }

        if (xOffset >= 128) {
            xOffset = 0;
        }
    }

    @Override
    public void render(Graphics2D g) {
        Paint originalPaint = g.getPaint();
        Composite originalComposite = g.getComposite();

        g.setPaint(paint);
        g.fillOval(Handler.get().getWidth() / 2 - radius, Handler.get().getHeight() / 2 - radius, radius * 2, radius * 2);

        g.setComposite(originalComposite);
        g.setPaint(originalPaint);

        for (int y = 0; y < HEIGHT; y += FOG_H) {
            for (int x = -FOG_W; x < WIDTH + FOG_W; x += FOG_W) {
                g.drawImage(getFogIntensityImg(), x + xOffset, y, null);
            }
        }
    }

    @Override
    public String getWeatherSoundEffect() {
        return null;
    }

    private BufferedImage getFogIntensityImg() {
        switch (intensity) {
            case NORMAL:
                return Assets.fogNormal;
            case HEAVY:
                return Assets.fogHeavy;
            default:
                return Assets.fogNormal;
        }
    }
}
