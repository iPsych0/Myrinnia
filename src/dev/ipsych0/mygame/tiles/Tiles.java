package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.MapLoader;

public class Tiles {
	
	// First try-out tiles
	
	public static Tiles[] tiles = new Tiles[4096];

	public static Tiles blackTile = new Tiles(Assets.black, 28, true);
	public static Tiles invisible = new Tiles(Assets.invisible, 736, false);

	
	
	
	/*
	 * Class data
	 */
	
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	protected int x, y;
	protected Rectangle bounds;
	protected int layer;
	protected boolean solid;
	
	public Tiles(BufferedImage texture, int id, boolean solid){
		this.texture = texture;
		this.id = id;
		this.solid = solid;
		
		bounds = new Rectangle(0, 0, TILEWIDTH, TILEHEIGHT);

	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
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
	
	public Rectangle tilePosition(float xOffset, float yOffset){
		//
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 32;
		bounds.height = 32;
		
		Rectangle ir = new Rectangle();
		int arSize = TILEWIDTH;
		ir.width = arSize;
		ir.height = arSize;
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
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
