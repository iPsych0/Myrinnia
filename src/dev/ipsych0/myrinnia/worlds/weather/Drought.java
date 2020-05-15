package dev.ipsych0.myrinnia.worlds.weather;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;
import java.io.Serializable;

public class Drought implements Weather, Serializable {

    // Drought atmosphere
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 1.0f};
    private static final Color[] colors = {new Color(218, 153, 83, 53), new Color(195, 73, 0, 132)};
    private static final RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, colors);
    private static final long serialVersionUID = -3385473571002315943L;

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        Paint originalPaint = g.getPaint();
        Composite originalComposite = g.getComposite();

        g.setPaint(paint);
        g.fillOval(Handler.get().getWidth() / 2 - radius, Handler.get().getHeight() / 2 - radius, radius * 2, radius * 2);
        g.setComposite(originalComposite);
        g.setPaint(originalPaint);
    }
}
