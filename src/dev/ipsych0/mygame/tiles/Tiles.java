package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.MapLoader;

public class Tiles {
	
	// Get all Tiled firstGids
	private static int[] tileGids = MapLoader.getTiledFirstGid(Handler.worldPath);
	
	// Set the Tiles-size to the maximum firstGID + the tilecount of the last tileset (aka the very last ID)
	public static Tiles[] tiles = new Tiles[(tileGids[tileGids.length - 1]
			+ MapLoader.getTileCount(Handler.worldPath, tileGids.length - 1))];
	
	/*
	 * Class data
	 */
	
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	protected int x, y;
	protected Rectangle bounds;
	protected boolean solid;
	
	public Tiles(BufferedImage texture, int id, boolean solid){
		this.texture = texture;
		this.id = id;
		this.solid = solid;
		
		bounds = new Rectangle(0, 0, TILEWIDTH, TILEHEIGHT);

	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void tick(){
		
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public void renderMiniMap(Graphics g, int x, int y){
		g.drawImage(texture, x, y, (int)TILEWIDTH * 25 / 100 , (int)TILEHEIGHT * 25 / 100 , null);
	}
	
	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public boolean isSolid(){
		return solid;
	}
	
	public static Tiles getTileByID(int id) {
		return tiles[id];
	}
	
	public int getId(){
		return id;
	}
}
