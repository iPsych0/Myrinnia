package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.tiles.AnimatedTile;
import dev.ipsych0.myrinnia.tiles.MovePermission;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.MapLoader;
import dev.ipsych0.splashscreen.SplashScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class SpriteSheet {


    private BufferedImage sheet;
    public static int[] firstGids = MapLoader.getTiledFirstGid();
    private int imageIndex;
    private int columns;
    private String path;

    public SpriteSheet(String path, boolean isTileSet) {
        this.sheet = ImageLoader.loadImage(path);
        this.path = path;

        if (isTileSet) {
            imageIndex = MapLoader.getImageIndex(Handler.initialWorldPath, path);
            columns = MapLoader.getTileColumns(Handler.initialWorldPath);
        }
    }

    public SpriteSheet(String path) {
        this.sheet = ImageLoader.loadImage(path);
        this.path = path;
    }

    /**
     * Crop function which creates the Tile and returns the image of the tile.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    private BufferedImage tileCrop(int x, int y, int width, int height, boolean postRendered) {

        // Multiply by 32 pixel Tiles
        x *= 32;
        y *= 32;

        int tileId;

        // Get the Tiled id in the image
        if (x == 0 && y == 0)
            tileId = 0;
        else if (y == 0)
            tileId = x / 32;
        else if (x == 0)
            tileId = (y / 32) * columns;
        else
            tileId = (y / 32) * columns + (x / 32);

        tileId = tileId + firstGids[imageIndex];

        SplashScreen.addLoadedElement();

        if (MapLoader.polygonTiles.get(tileId) != null) {
            int size = MapLoader.polygonTiles.get(tileId).size();
            List<Point> points = MapLoader.polygonTiles.get(tileId);
            int[] xCoords = new int[size];
            int[] yCoords = new int[size];
            for (int i = 0; i < size; i++) {
                xCoords[i] = (int) points.get(i).getX();
                yCoords[i] = (int) points.get(i).getY();
            }
            if (MapLoader.animationMap.get(tileId) != null) {
                Tile.tiles[tileId] = new AnimatedTile(sheet.getSubimage(x, y, width, height), tileId, xCoords, yCoords, MapLoader.animationMap.get(tileId));
            } else {
                BufferedImage img = sheet.getSubimage(x, y, width, height);
                if (isTileTransparent(img)) {
                    return null;
                }
                Tile.tiles[tileId] = new Tile(sheet.getSubimage(x, y, width, height), tileId, xCoords, yCoords);
            }
        } else {
            if (MapLoader.animationMap.get(tileId) != null) {
                Tile.tiles[tileId] = new AnimatedTile(sheet.getSubimage(x, y, width, height), tileId, MapLoader.solidTiles.get(tileId), MapLoader.postRenderTiles.get(tileId), MapLoader.animationMap.get(tileId));
            } else {
                BufferedImage img = sheet.getSubimage(x, y, width, height);
                if (isTileTransparent(img)) {
                    return null;
                }
                Tile.tiles[tileId] = new Tile(img, tileId, MapLoader.solidTiles.get(tileId), MapLoader.postRenderTiles.get(tileId));
            }
        }

        // If we're loading a movement permission tile, set the right permission
        if (MovePermission.map.get(tileId) != null) {
            Tile.tiles[tileId].setPermission(MovePermission.map.get(tileId));
        }

        return sheet.getSubimage(x, y, width, height);
    }

    private boolean isTileTransparent(BufferedImage img) {
        int transparencyCount = 0;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if (isPixelTransparent(img, i, j)) {
                    transparencyCount++;
                } else {
                    break;
                }
            }
        }
        return transparencyCount == img.getWidth() * img.getHeight();
    }

    private boolean isPixelTransparent(BufferedImage img, int x, int y) {
        int pixel = img.getRGB(x, y);
        return (pixel >> 24) == 0x00;
    }

    /**
     * Default crop function with custom width and height and not-solid Tile
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImage tileCrop(int x, int y) {
        return tileCrop(x, y, 32, 32, false);
    }

    /**
     * Crops images from SpriteSheets that are not Tiled Map tiles with custom width and height
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return cropped non-tile image
     */
    public BufferedImage imageCrop(int x, int y, int width, int height) {
        return imageCrop(x, y, width, height, false);
    }

    public BufferedImage imageCrop(int x, int y, int width, int height, boolean customXandY) {

        // Multiply by 32 pixel Tiles
        if (!customXandY) {
            x *= 32;
            y *= 32;
        }

        SplashScreen.addLoadedElement();

        return sheet.getSubimage(x, y, width, height);
    }

    /**
     * Crop out an array of NPC animations with custom x/y & width/height
     *
     * @param x      absolute xPos
     * @param y      absolute yPos
     * @param width  absolute width
     * @param height absolute height
     * @return array of NPC animations
     */
    public BufferedImage[] npcCrop(int x, int y, int width, int height, int frames) {
        BufferedImage[] imgs = new BufferedImage[frames];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = sheet.getSubimage(x + (i * width), y, width, height);
            SplashScreen.addLoadedElement();
        }
        return imgs;
    }

    public BufferedImage[] npcCrop(int x, int y, int width, int height) {
        return npcCrop(x * Creature.DEFAULT_CREATURE_WIDTH, y * Creature.DEFAULT_CREATURE_HEIGHT, width, height, 3);
    }

    public BufferedImage[] npcCrop(int x, int y) {
        // Crop out a 32x32 NPC
        return npcCrop(x * Creature.DEFAULT_CREATURE_WIDTH, y * Creature.DEFAULT_CREATURE_HEIGHT,
                Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 3);
    }

    public BufferedImage[] npcCrop(int x, int y, int frames) {
        // Crop out a 32x32 NPC
        return npcCrop(x * Creature.DEFAULT_CREATURE_WIDTH, y * Creature.DEFAULT_CREATURE_HEIGHT,
                Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, frames);
    }

    public BufferedImage singleNpcCrop(int x, int y) {
        // Crop out a 32x32 NPC
        return singleNpcCrop(x * Creature.DEFAULT_CREATURE_WIDTH, y * Creature.DEFAULT_CREATURE_HEIGHT,
                Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
    }

    /**
     * Crop out a single NPC frame with custom x/y & width/height
     *
     * @param x      absolute xPos
     * @param y      absolute yPos
     * @param width  absolute width
     * @param height absolute height
     * @return cropped NPC image
     */
    public BufferedImage singleNpcCrop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }

    /**
     * Crops images from SpriteSheets that are not Tiled Map tiles
     *
     * @param x
     * @param y
     * @return cropped non-tile image
     */
    public BufferedImage imageCrop(int x, int y) {
        return imageCrop(x, y, 32, 32, false);
    }

    public BufferedImage getSheet() {
        return sheet;
    }

    public void setSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public String getPath() {
        return path;
    }
}
