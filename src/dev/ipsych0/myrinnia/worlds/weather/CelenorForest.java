package dev.ipsych0.myrinnia.worlds.weather;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;

public class CelenorForest implements Weather {
    // Swamp atmosphere
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 0.8f, 1.0f};
    private static final Color[] colors = {new Color(154, 57, 239, 8), new Color(98, 32, 205, 80), new Color(61, 31, 135, 160)};
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
