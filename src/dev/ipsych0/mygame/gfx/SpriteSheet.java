package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.MapLoader;

public class SpriteSheet {

	
	private BufferedImage sheet;
	public static int[] firstGids = MapLoader.getTiledFirstGid(Handler.initialWorldPath);
	private int imageIndex;
	private int columns;
	
	public SpriteSheet(String path, boolean tile){
		this.sheet = ImageLoader.loadImage(path);
		
		if(tile) {
			imageIndex = MapLoader.getImageIndex(Handler.initialWorldPath, path);
			columns = MapLoader.getTileColumns(Handler.initialWorldPath, imageIndex);
			
			// Calculate the image width & height
//			imageWidth = columns * 32;
//			imageHeight = tileCount / columns * 32;
		}
	}
	
	public SpriteSheet(String path){
		this.sheet = ImageLoader.loadImage(path);
	}
	
	/**
	 * Crop function that takes in solid parameter
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isSolid
	 * @return
	 */
	public BufferedImage tileCrop(int x, int y, int width, int height, boolean isSolid){
				
		int tileId = -1;
		
		// Get the Tiled id in the image
		if(y == 0) 
			tileId = 0 + (x / 32);
		else if(x == 0) 
			tileId = (y / 32) * columns;
		else if(x == 0 && y == 0)
			tileId = 0;
		else
			tileId = (y / 32) * columns + (x / 32);

		// Set the tile image
		Tiles.tiles[(tileId+firstGids[imageIndex])] = new Tiles(sheet.getSubimage(x, y, width, height), (tileId+firstGids[imageIndex]), isSolid);

		return sheet.getSubimage(x, y, width, height);
	}
	
	/**
	 * Default crop function returns not-solid tiles
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage tileCrop(int x, int y, int width, int height){
		
		int tileId = -1;

		// Get the Tiled id in the image
		if(y == 0) 
			tileId = 0 + (x / 32);
		else if(x == 0) 
			tileId = (y / 32) * columns;
		else if(x == 0 && y == 0)
			tileId = 0;
		else
			tileId = (y / 32) * columns + (x / 32);

		// Set the tile image
		Tiles.tiles[(tileId+firstGids[imageIndex])] = new Tiles(sheet.getSubimage(x, y, width, height), (tileId+firstGids[imageIndex]), false);
		
		return sheet.getSubimage(x, y, width, height);
	}

	/**
	 * Crops images from SpriteSheets that are not Tiled tiles
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return cropped non-tile image
	 */
	public BufferedImage imageCrop(int x, int y, int width, int height){
		return sheet.getSubimage(x, y, width, height);
	}

	public BufferedImage getSheet() {
		return sheet;
	}

	public void setSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
}
