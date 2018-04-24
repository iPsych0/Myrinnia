package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import dev.ipsych0.mygame.mapeditor.MapLoader;

public class SpriteSheet {

	
	private BufferedImage sheet;
	private String path;
	
	public SpriteSheet(String path){
		this.path = path;
		this.sheet = ImageLoader.loadImage(path);
	}
	
	public BufferedImage tileCrop(int x, int y, int width, int height){
		
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
