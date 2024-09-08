package dev.ipsych0.myrinnia.worlds.weather;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;
import java.io.Serializable;

public class Swamp implements Weather, Serializable {

    // Swamp atmosphere
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 0.8f, 1.0f};
    private static final Color[] colors = {new Color(57, 170, 136, 16), new Color(21, 74, 62, 192), new Color(18, 66, 58, 232)};
    private static final RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, colors);
    private static final long serialVersionUID = 304653569636157297L;

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

    @Override
    public String getWeatherSoundEffect() {
        return null;
    }
}
