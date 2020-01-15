package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class Rain implements Weather {

    private static final int WIDTH = Handler.get().getWidth(), HEIGHT = Handler.get().getHeight();
    private static final int RAIN_W = 128, RAIN_H = 32;
    private int xOffset, yOffset;

    // Rain atmosphere
    private static final int radius = 800;
    private static final float[] fractions = {0.0f, 1.0f};
    private static final Color[] rainColors = {new Color(0, 13, 35, 27), new Color(0, 13, 35, 127)};
    private static final RadialGradientPaint rainPaint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, rainColors);

    // Thunder atmosphere
    private static int centerThunderAlpha = 255, outerThunderAlpha = 85;
    private static final Color[] thunderColors = {new Color(255, 255, 217, centerThunderAlpha), new Color(255, 255, 245, outerThunderAlpha)};
    private static RadialGradientPaint thunderPaint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
            getHeight() / 2, radius, fractions, thunderColors);

    // Thunder settings
    private boolean withThunder;
    private boolean hasRolledThunder;
    private int thunderRoll;
    private boolean renderingThunder;
    private int thunderTimer, thunderTime = 180;
    private boolean doubleThunderRolled;

    public Rain(boolean withThunder) {
        this.withThunder = withThunder;
    }

    public Rain() {
        this.withThunder = false;
    }

    @Override
    public void tick() {
        xOffset += 1;
        yOffset += 1;

        if (Handler.get().getPlayer().getxMove() > 0 && !Handler.get().getGameCamera().isAtAnyBound()) {
            xOffset += Handler.get().getPlayer().getSpeed();
        }
        if (Handler.get().getPlayer().getyMove() > 0 && !Handler.get().getGameCamera().isAtAnyBound()) {
            yOffset -= Handler.get().getPlayer().getSpeed();
        }

        if (xOffset >= 32 && yOffset >= 32) {
            xOffset = 0;
            yOffset = 0;
        }

        if (withThunder) {
            thunderTimer++;
            if (thunderTimer >= thunderTime) {
                if (!hasRolledThunder) {
                    thunderRoll = Handler.get().getRandomNumber(0, 540);
                    hasRolledThunder = true;
                    thunderTime += thunderRoll;
                } else {
                    renderingThunder = true;
                }
            }

        }
    }

    @Override
    public void render(Graphics2D g) {
        Paint originalPaint = g.getPaint();
        Composite originalComposite = g.getComposite();

        g.setPaint(rainPaint);
        g.fillOval(Handler.get().getWidth() / 2 - radius, Handler.get().getHeight() / 2 - radius, radius * 2, radius * 2);

        g.setComposite(originalComposite);
        g.setPaint(originalPaint);

        for (int y = -RAIN_H; y < HEIGHT + RAIN_H; y += RAIN_H) {
            for (int x = -RAIN_W; x < WIDTH + RAIN_W; x += RAIN_W) {
                g.drawImage(Assets.rain, x - xOffset, y + yOffset, null);
            }
        }
    }

    public void renderThunder(Graphics2D g) {
        if (withThunder && renderingThunder) {
            Paint originalPaint = g.getPaint();
            Composite originalComposite = g.getComposite();

            g.setPaint(thunderPaint);
            g.fillOval(Handler.get().getWidth() / 2 - radius, Handler.get().getHeight() / 2 - radius, radius * 2, radius * 2);

            g.setComposite(originalComposite);
            g.setPaint(originalPaint);

            centerThunderAlpha -= 3;
            outerThunderAlpha -= 1;
            if (centerThunderAlpha < 0) {
                centerThunderAlpha = 0;
            }
            if (outerThunderAlpha < 0) {
                outerThunderAlpha = 0;
            }

            // 1 in 3 chance for double thunder
            if (!doubleThunderRolled && centerThunderAlpha <= 255 - (17 * 3) && outerThunderAlpha <= 255 - (17 * 1)) {
                doubleThunderRolled = true;
                int rnd = Handler.get().getRandomNumber(1, 3);
                if (rnd == 1) {
                    centerThunderAlpha = 255;
                    outerThunderAlpha = 85;
                }
            }

            thunderColors[0] = new Color(255, 255, 217, centerThunderAlpha);
            thunderColors[1] = new Color(255, 255, 245, outerThunderAlpha);
            thunderPaint = new RadialGradientPaint(Handler.get().getWidth() / 2, Handler.get().
                    getHeight() / 2, radius, fractions, thunderColors);

            // Reset thunder timers
            if (centerThunderAlpha == 0 && outerThunderAlpha == 0) {
                renderingThunder = false;
                hasRolledThunder = false;
                thunderTimer = 0;
                thunderTime = 180;
                centerThunderAlpha = 255;
                outerThunderAlpha = 85;
                doubleThunderRolled = false;
            }
        }
    }
}
