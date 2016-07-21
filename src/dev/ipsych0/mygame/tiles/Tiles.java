package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiles {
	
	//STATIC VARIABLES
	
	public static Tiles[] tiles = new Tiles[256];
	public static Tiles lavaTile = new LavaTile(0);
	public static Tiles grassTile = new GrassTile(1);
	public static Tiles dirtTile = new DirtTile(2);
	public static Tiles rockTile = new RockTile(3);
	public static Tiles waterTile = new WaterTile(4);
	public static Tiles caveTile = new CaveTile(5);
	public static Tiles iceTile = new IceTile(6);
	public static Tiles sandTile = new SandTile(7);
	public static Tiles snowTile = new SnowTile(8);
	public static Tiles blackTile = new BlackTile(9);
	public static Tiles greenHouseRoof = new GreenHouseRoof(10);
	public static Tiles greenHouseWall = new GreenHouseWall(11);
	public static Tiles greenHouseEntrance = new GreenHouseEntrance(12);
	
	//CLASS
	
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tiles(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
		
	}
	

	public void tick(){
		
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public int getId(){
		return id;
	}

}
