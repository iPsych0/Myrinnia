package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class SpriteSheet {


    private BufferedImage sheet;
    public static int[] firstGids = MapLoader.getTiledFirstGid(Handler.initialWorldPath);
    private int imageIndex;
    private int columns;

    public SpriteSheet(String path, boolean isTileSet) {
        this.sheet = ImageLoader.loadImage(path);

        if (isTileSet) {
            imageIndex = MapLoader.getImageIndex(Handler.initialWorldPath, path);
            columns = MapLoader.getTileColumns(Handler.initialWorldPath, imageIndex);
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
     * @return
     */
    public BufferedImage tileCrop(int x, int y, int width, int height, boolean postRendered) {

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


        if (MapLoader.polygonTiles.get(tileId) != null) {
            int size = MapLoader.polygonTiles.get(tileId).size();
            List<Point> points = MapLoader.polygonTiles.get(tileId);
            int[] xCoords = new int[size];
            int[] yCoords = new int[size];
            for (int i = 0; i < size; i++) {
                xCoords[i] = (int) points.get(i).getX();
                yCoords[i] = (int) points.get(i).getY();
            }
            Tiles.tiles[tileId] = new Tiles(sheet.getSubimage(x, y, width, height), tileId, xCoords, yCoords);
        } else {
            Tiles.tiles[tileId] = new Tiles(sheet.getSubimage(x, y, width, height), tileId, MapLoader.solidTiles.get(tileId), MapLoader.postRenderTiles.get(tileId));
        }

        return sheet.getSubimage(x, y, width, height);
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
    public BufferedImage tileCrop(int x, int y, int width, int height) {
        return tileCrop(x, y, width, height, false);
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
     * Default crop function with custom width and height and not-solid Tile
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImage tileCrop(int x, int y, boolean postRendered) {
        return tileCrop(x, y, 32, 32, postRendered);
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
