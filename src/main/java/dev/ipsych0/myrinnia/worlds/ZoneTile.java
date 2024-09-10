package dev.ipsych0.myrinnia.worlds;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ZoneTile extends Rectangle {

    private Zone zone;
    private String customZoneName;
    private String customZoneMusic;
    private int goToX, goToY;
    private Creature.Direction direction;

    public ZoneTile(Zone zone, int fromX, int fromY, int width, int height, int goToX, int goToY, String customZoneName, String customZoneMusic, Creature.Direction direction) {
        super(fromX, fromY, width, height);

        this.goToX = goToX;
        this.goToY = goToY;
        this.zone = zone;
        this.customZoneName = customZoneName;
        this.customZoneMusic = customZoneMusic;
        this.direction = direction;
    }

    public void tick() {
        if (direction != null) {
            if (this.intersects(Handler.get().getPlayer().getFullBounds(0, 0))) {
                if (Handler.get().getKeyManager().talk) {
                    Handler.get().goToWorld(zone, goToX, goToY, customZoneName, customZoneMusic);
                }
            }
        } else if (this.intersects(Handler.get().getPlayer().getCollisionBounds(0, 0))) {
            Handler.get().goToWorld(zone, goToX, goToY, customZoneName, customZoneMusic);
        }
    }

    public void render(Graphics2D g) {
        if (direction != null && this.intersects(Handler.get().getPlayer().getFullBounds(0, 0))) {
            drawHoverCorners(g, 1, 1, Color.BLACK);
            drawHoverCorners(g, 0, 0, Color.YELLOW);

            String interactKey = KeyEvent.getKeyText(KeyManager.interactKey);
            Text.drawString(g, "Press '" + interactKey + "' to interact", (int) (x + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + height / 2 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font14);
        } else if (direction != null && new Rectangle((int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height).contains(Handler.get().getMouse())) {
            drawHoverCorners(g, 1, 1, Color.BLACK);
            drawHoverCorners(g, 0, 0, Color.YELLOW);

            String interactKey = KeyEvent.getKeyText(KeyManager.interactKey);
            Text.drawString(g, "Press '" + interactKey + "' to interact", (int) (x + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + height / 2 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font14);
        }
        if (Handler.debugZones) {
            g.setColor(Color.MAGENTA);
            g.drawRect(x - (int) Handler.get().getGameCamera().getxOffset(), y - (int) Handler.get().getGameCamera().getyOffset(), width, height);
        }
    }

    private void drawHoverCorners(Graphics2D g, int xOffset, int yOffset, Color color) {
        Stroke defaultStroke = g.getStroke();

        g.setColor(color);
        g.setStroke(new BasicStroke(2));

        // Top left corner
        g.drawLine((int) (xOffset + x - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x + (6 * (width / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + x - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + (6 * (height / 32)) - Handler.get().getGameCamera().getyOffset()));

        // Top right corner
        g.drawLine((int) (xOffset + x + width - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x + width - (6 * (width / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + x + width - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x + width - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + (6 * (height / 32)) - Handler.get().getGameCamera().getyOffset()));

        // Bottom left corner
        g.drawLine((int) (xOffset + x - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x + (6 * (width / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + x - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - (6 * (height / 32)) - Handler.get().getGameCamera().getyOffset()));

        // Bottom right corner
        g.drawLine((int) (xOffset + x + width - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x + width - (6 * (width / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + x + width - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + x + width - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + y + height - (6 * (height / 32)) - Handler.get().getGameCamera().getyOffset()));

        g.setStroke(defaultStroke);
    }
}
