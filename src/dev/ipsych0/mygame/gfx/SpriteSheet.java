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
	private int[] firstGids;
	private int imageIndex;
	private int columns;
	
	public SpriteSheet(String path, boolean tile){
		this.sheet = ImageLoader.loadImage(path);
		
		if(tile) {
			firstGids = MapLoader.getTiledFirstGid(Handler.worldPath);
			imageIndex = MapLoader.getImageIndex(Handler.worldPath, path);
			columns = MapLoader.getTileColumns(Handler.worldPath, imageIndex);
			
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
		
		if(y == 0) 
			tileId = 0 + (x / 32);
		else if(x == 0) 
			tileId = (y / 32) * columns;
		else if(x == 0 && y == 0)
			tileId = 0;
		else
			tileId = (y / 32) * columns + (x / 32);

		Tiles.tiles[(tileId+firstGids[imageIndex])] = new Tiles(sheet.getSubimage(x, y, width, height), (tileId+firstGids[imageIndex]), isSolid);

		
		/*
		 * Hier shit doen als:
		 * Haal op hoeveelste index het path is in de tilesets.
		 * Bijvoorbeeld: '/textures/trees.png' = 3e index
		 * Dan haal de Tiled Global IDs op uit MapLoader en pak de GID van die tileset (de 1e Tile #)
		 * Tel vervolgens de x/y coordinaten bij de locatie op om de juiste ID te krijgen
		 * Vervolgens zet "Tiles.tiles[ID] = new Tiles(sheet.getSubImage(x,y,width,height), ID);
		 * 
		 * ALSO!!!
		 * Verander in Tiles class ('= new Tiles[4096]') naar = new Tiles[MapLoader.getGIDs.laatsteFirstGID+TileCount]
		 * voor efficiënt en automatisch aanpasbare grootte!
		 */
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

		if(y == 0) 
			tileId = 0 + (x / 32);
		else if(x == 0) 
			tileId = (y / 32) * columns;
		else if(x == 0 && y == 0)
			tileId = 0;
		else
			tileId = (y / 32) * columns + (x / 32);

		Tiles.tiles[(tileId+firstGids[imageIndex])] = new Tiles(sheet.getSubimage(x, y, width, height), (tileId+firstGids[imageIndex]), false);
		
		
		/*
		 * Hier shit doen als:
		 * Haal op hoeveelste index het path is in de tilesets.
		 * Bijvoorbeeld: '/textures/trees.png' = 3e index
		 * Dan haal de Tiled Global IDs op uit MapLoader en pak de GID van die tileset (de 1e Tile #)
		 * Tel vervolgens de x/y coordinaten bij de locatie op om de juiste ID te krijgen
		 * Vervolgens zet "Tiles.tiles[ID] = new Tiles(sheet.getSubImage(x,y,width,height), ID);
		 * 
		 * ALSO!!!
		 * Verander in Tiles class ('= new Tiles[4096]') naar = new Tiles[MapLoader.getGIDs.laatsteFirstGID+TileCount]
		 * voor efficiënt en automatisch aanpasbare grootte!
		 */
		return sheet.getSubimage(x, y, width, height);
	}

	public BufferedImage imageCrop(int x, int y, int width, int height){
		return sheet.getSubimage(x, y, width, height);
	}
}
