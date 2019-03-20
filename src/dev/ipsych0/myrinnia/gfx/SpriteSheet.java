package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.image.BufferedImage;

public class SpriteSheet {


    private BufferedImage sheet;
    public static int[] firstGids = MapLoader.getTiledFirstGid(Handler.initialWorldPath);
    private int imageIndex;
    private int columns;

    public SpriteSheet(String path, boolean tile) {
        this.sheet = ImageLoader.loadImage(path);

        if (tile) {
            imageIndex = MapLoader.getImageIndex(Handler.initialWorldPath, path);
            columns = MapLoader.getTileColumns(Handler.initialWorldPath, imageIndex);

            // Calculate the image width & height
//			imageWidth = columns * 32;
//			imageHeight = tileCount / columns * 32;
        }
    }

    public SpriteSheet(String path) {
        this.sheet = ImageLoader.loadImage(path);
    }

    /**
     * Crop function which creates the Tile and returns the image of the tile.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param isSolid
     * @return
     */
    public BufferedImage tileCrop(int x, int y, int width, int height, boolean isSolid, boolean postRendered) {

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

        // Set the tile image
        Tiles.tiles[(tileId + firstGids[imageIndex])] = new Tiles(sheet.getSubimage(x, y, width, height), (tileId + firstGids[imageIndex]), isSolid, postRendered);

        return sheet.getSubimage(x, y, width, height);
    }

    /**
     * Default crop function with custom width and height and not-solid Tile
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImage tileCrop(int x, int y, boolean isSolid) {
        return tileCrop(x, y, 32, 32, isSolid, false);
    }

    /**
     * Default crop function with custom width and height and not-solid Tile
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImage tileCrop(int x, int y, boolean isSolid, boolean postRendered) {
        return tileCrop(x, y, 32, 32, isSolid, postRendered);
    }

    /**
     * Default crop function with custom width and height and not-solid Tile
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public BufferedImage tileCrop(int x, int y, int width, int height) {
        return tileCrop(x, y, width, height, false, false);
    }

    /**
     * Default crop function with default width and height and not-solid Tile
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImage tileCrop(int x, int y) {
        return tileCrop(x, y, 32, 32, false, false);
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

        // Multiply by 32 pixel Tiles
        x *= 32;
        y *= 32;

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
        return imageCrop(x, y, 32, 32);
    }

    public BufferedImage getSheet() {
        return sheet;
    }

    public void setSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }
}
