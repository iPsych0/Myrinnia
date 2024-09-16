package dev.ipsych0.myrinnia.tiles;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class Tile {
    public static Map<Integer, Boolean> solidTiles = new HashMap<>();
    public static Map<Integer, Boolean> postRenderTiles = new HashMap<>();
    public static Map<Integer, List<Point>> polygonTiles = new HashMap<>();
    public static Map<Integer, Map<Integer, Integer>> animationMap = new HashMap<>();

    // In-game Tiles
    public static Tile[] tiles;

    public static final int TILEWIDTH = 32, TILEHEIGHT = 32;

    private BufferedImage texture;
    private final int id;
    private boolean solid, postRendered;
    private int[] xPoints, yPoints;
    private Polygon polyBounds;
    private boolean initialized, reset;
    private int lastX = -1, lastY = -1;
    private String permission;
    private Animation animation;
    private Map<Integer, Integer> animationTiles;
    private static final Integer DEFAULT_ANIMATION_SPEED = 500;

    public void tick() {
        if (animation != null) {
            animation.tick();
        } else {
            loadAnimation();
        }
    }

    private void loadAnimation() {
        if (animationTiles == null || animationTiles.isEmpty()) {
            return;
        }
        List<BufferedImage> tiles = animationTiles.keySet()
                .stream()
                .map(key -> Tile.tiles[key].getTexture())
                .collect(Collectors.toList());

        int speed = animationTiles.values().stream()
                .findFirst()
                .orElse(DEFAULT_ANIMATION_SPEED);

        animation = new Animation(speed, tiles);
    }

    public void render(Graphics2D g, int x, int y) {
        if (animation != null) {
            g.drawImage(animation.getCurrentFrame(), x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
            return;
        }

        g.drawImage(texture, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
        if (Handler.debugCollision && polyBounds != null) {
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

    public Polygon getPolyBounds(int xPos, int yPos) {
        // Reset the polygon to other occurences of this Tile
        if (xPos != lastX || yPos != lastY) {
            // Don't reset the first time
            if (initialized) {
                reset = true;
            }
            initialized = false;
        }
        if (!initialized && polyBounds != null) {
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

            // Create the new polyBounds
            polyBounds = new Polygon(xPoints, yPoints, (xPoints.length + yPoints.length) / 2);

            initialized = true;
            reset = false;
            lastX = xPos;
            lastY = yPos;
        }
        return polyBounds;
    }

    /*
     * Make sure that we validate setter logic for solid/postrender changes
     */
    public void setSolid(boolean solid) {
        setSolidAndPostRendered(solid, postRendered);
    }

    public void setPostRendered(boolean postRendered) {
        setSolidAndPostRendered(solid, postRendered);
    }

    public void setSolidAndPostRendered(boolean solid, boolean postRendered) {
        if (this.postRendered && solid) {
            this.solid = true;
            this.postRendered = false;
        } else {
            this.solid = solid;
            this.postRendered = postRendered;
        }
    }

    public static class TileBuilder {
        private boolean solid;
        private boolean postRendered;

        public TileBuilder solid(boolean solid) {
            setSolidAndPostRendered(solid, postRendered);
            return this;
        }

        public TileBuilder postRendered(boolean postRendered) {
            setSolidAndPostRendered(solid, postRendered);
            return this;
        }

        public void setSolidAndPostRendered(boolean solid, boolean postRendered) {
            if (this.postRendered && solid) {
                this.solid = true;
                this.postRendered = false;
            } else {
                this.solid = solid;
                this.postRendered = postRendered;
            }
        }
    }
}
