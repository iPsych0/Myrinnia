package dev.ipsych0.myrinnia.worlds.weather;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;

public class SandStorm implements Weather, Serializable {

    private static final int WIDTH = Handler.get().getWidth(), HEIGHT = Handler.get().getHeight();
    private static final int SAND_W = 128, SAND_H = 32;
    private static final long serialVersionUID = -9222286332549262565L;
    private int xOffset;

    // Shading effect
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 1.0f};
    private static final Color[] colors = {new Color(177, 184, 94, 56), new Color(240, 243, 127, 140)};
    private static final RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, colors);

    @Override
    public void tick() {
        xOffset += 2;
        if (Handler.get().getPlayer().getxMove() > 0) {
            xOffset += Handler.get().getPlayer().getSpeed();
        }
        if (xOffset >= 32) {
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

        for (int y = 0; y < HEIGHT; y += SAND_H) {
            for (int x = -SAND_W; x < WIDTH + SAND_W; x += SAND_W) {
                g.drawImage(Assets.sandStorm, x - xOffset, y, null);
            }
        }
    }

    @Override
    public String getWeatherSoundEffect() {
        return null;
    }
}
