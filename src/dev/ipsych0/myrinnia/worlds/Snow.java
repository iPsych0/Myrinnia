package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;

public class Snow implements Weather, Serializable {

    private static final int WIDTH = Handler.get().getWidth(), HEIGHT = Handler.get().getHeight();
    private static final int SNOW_W = 128, SNOW_H = 32;
    private static final long serialVersionUID = -746170327884092808L;
    private int xOffset, yOffset;

    // Shading effect
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 1.0f};
    private static final Color[] colors = {new Color(139, 170, 184, 56), new Color(167, 233, 243, 140)};
    private static final RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, colors);

    @Override
    public void tick() {
        xOffset += 1;
        yOffset += 1;

        if (Handler.get().getPlayer().getxMove() > 0) {
            xOffset += Handler.get().getPlayer().getSpeed();
        }
        if (Handler.get().getPlayer().getyMove() < 0) {
            yOffset += Handler.get().getPlayer().getSpeed();
        }

        if (xOffset >= 32 || yOffset >= 32) {
            xOffset = 0;
            yOffset = 0;
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

        for (int y = -SNOW_H; y < HEIGHT + SNOW_H; y += SNOW_H) {
            for (int x = -SNOW_W; x < WIDTH + SNOW_W; x += SNOW_W) {
                g.drawImage(Assets.snow, x - xOffset, y + yOffset, null);
            }
        }
    }
}
