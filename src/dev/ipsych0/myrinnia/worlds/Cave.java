package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;
import java.io.Serializable;

public class Cave implements Weather, Serializable {

    // Cave atmosphere
    private static final int radius = 1600;
    private static final float[] fractions = {0.0f, 0.25f, 1.0f};
    private static final Color[] colors = {new Color(155, 139, 199, 26), new Color(25, 25, 50, 218), new Color(14, 14, 20, 248)};
    private static final long serialVersionUID = 6694301119178288241L;
    private static RadialGradientPaint paint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, colors);

    @Override
    public void tick() {
         paint = new RadialGradientPaint((int) (Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset()),
                 (int) (Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset()),
                 radius, fractions, colors);
    }

    @Override
    public void render(Graphics2D g) {
        Paint originalPaint = g.getPaint();
        Composite originalComposite = g.getComposite();

        g.setPaint(paint);
        g.fillOval((int) (Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset()) - radius, (int) (Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset()) - radius, radius * 2, radius * 2);
        g.setComposite(originalComposite);
        g.setPaint(originalPaint);
    }
}
