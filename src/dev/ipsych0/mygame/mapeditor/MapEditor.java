package dev.ipsych0.mygame.mapeditor;

import javax.swing.JFrame;

import dev.ipsych0.mygame.utils.Utils;

public class MapEditor extends JFrame {
	
	int width, height, spawnX, spawnY;
	int[][] tiles;
	
	private void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		
		// Splits worlds files by spaces and puts them all in an array
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				// Loads in the actual tiles, +4 to skip the first 4 pieces of metadata
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
}
