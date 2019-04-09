package dev.ipsych0.myrinnia.tiles;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.SpriteSheet;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tiles {

    // Set the Tiles-size to the maximum firstGID + the tilecount of the last tileset (aka the very last ID)
    public static Tiles[] tiles = new Tiles[(SpriteSheet.firstGids[SpriteSheet.firstGids.length - 1]
            + MapLoader.getTileCount(Handler.initialWorldPath, SpriteSheet.firstGids.length - 1))];


    public static final int TILEWIDTH = 32, TILEHEIGHT = 32;

    private BufferedImage texture;
    private final int id;
    private int x, y;
    private boolean solid, postRendered;
    private int[] xPoints, yPoints;
    private Polygon bounds;
    private boolean initialized, reset;
    private int lastX = -1, lastY = -1;

    public Tiles(BufferedImage texture, int id, boolean solid) {
        this.texture = texture;
        this.id = id;
        this.solid = solid;
    }

    public Tiles(BufferedImage texture, int id, int[] x, int[] y) {
        this.texture = texture;
        this.id = id;
        this.solid = true;
        this.xPoints = x;
        this.yPoints = y;
        this.bounds = new Polygon(x, y, (x.length + y.length) / 2);
    }

    public Tiles(BufferedImage texture, int id, boolean solid, boolean postRendered) {
        this.texture = texture;
        this.id = id;
        // Always false, because there is no point in post-render if an Entity can't walk behind this Tile
        if (postRendered && solid) {
            this.solid = true;
            this.postRendered = false;
        } else {
            this.solid = solid;
            this.postRendered = postRendered;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, null);
        if (Handler.debugCollision && bounds != null) {
            int[] xArr, yArr;
            xArr = xPoints.clone();
            yArr = yPoints.clone();
            for (int i = 0; i < xArr.length; i++) {
                xArr[i] = xArr[i] - (int) Handler.get().getGameCamera().getxOffset();
            }
            for (int i = 0; i < yArr.length; i++) {
                yArr[i] = yArr[i] - (int) Handler.get().getGameCamera().getyOffset();
            }
            g.setColor(Color.BLUE);
            g.fillPolygon(xArr, yArr, (xArr.length + yArr.length) / 2);
        }
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public boolean isSolid() {
        return solid;
    }

    public int getId() {
        return id;
    }

    public boolean isPostRendered() {
        return postRendered;
    }

    public void setPostRendered(boolean postRendered) {
        this.postRendered = postRendered;
    }

    public Polygon getBounds() {
        return bounds;
    }

    public Polygon getBounds(int xPos, int yPos) {
        // Reset the polygon to other occurences of this Tile
        if (xPos != lastX || yPos != lastY) {
            // Don't reset the first time
            if (initialized) {
                reset = true;
            }
            initialized = false;
        }
        if (!initialized && bounds != null) {
            for (int i = 0; i < xPoints.length; i++) {
                // Subtract the old coordinates
                if (reset) {
                    xPoints[i] = xPoints[i] - (lastX * TILEWIDTH);
                }
                // Set the new coordinates
                xPoints[i] = xPoints[i] + (xPos * TILEWIDTH);
            }
            for (int i = 0; i < yPoints.length; i++) {
                // Subtract the old coordinates
                if (reset) {
                    yPoints[i] = yPoints[i] - (lastY * TILEHEIGHT);
                }
                // Set the new coordinates
                yPoints[i] = yPoints[i] + (yPos * TILEHEIGHT);
            }

            // Create the new bounds
            bounds = new Polygon(xPoints, yPoints, (xPoints.length + yPoints.length) / 2);

            initialized = true;
            reset = false;
            lastX = xPos;
            lastY = yPos;
        }
        return bounds;
    }

    public void setBounds(Polygon bounds) {
        this.bounds = bounds;
    }
}
