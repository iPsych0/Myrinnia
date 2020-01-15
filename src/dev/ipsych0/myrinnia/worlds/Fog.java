package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class Fog implements Weather {

    private static final int WIDTH = Handler.get().getWidth(), HEIGHT = Handler.get().getHeight();
    private static final int FOG_W = 128, FOG_H = 32;
    private int xOffset;

    // Shading effect
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 1.0f};
    private static final Color[] colors = {new Color(139, 170, 184, 53), new Color(167, 233, 243, 107)};
    private static final RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, colors);

    @Override
    public void tick() {
        xOffset += 1;
        if (Handler.get().getPlayer().getxMove() < 0 && !Handler.get().getGameCamera().isAtAnyBound()) {
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
                g.drawImage(Assets.fog, x + xOffset, y, null);
            }
        }
    }
}
