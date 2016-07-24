package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiles {
	
	//STATIC VARIABLES
	
	public static Tiles[] tiles = new Tiles[256];
	public static Tiles lavaTile = new LavaTile(100);
	public static Tiles grassTile = new GrassTile(101);
	public static Tiles dirtTile = new DirtTile(102);
	public static Tiles rockTile = new RockTile(103);
	public static Tiles waterTile = new WaterTile(104);
	public static Tiles caveTile = new CaveTile(105);
	public static Tiles iceTile = new IceTile(106);
	public static Tiles sandTile = new SandTile(107);
	public static Tiles snowTile = new SnowTile(108);
	public static Tiles blackTile = new BlackTile(109);
	public static Tiles greenHouseRoof = new GreenHouseRoof(110);
	public static Tiles greenHouseWall = new GreenHouseWall(111);
	public static Tiles greenHouseEntrance = new GreenHouseEntrance(112);
	
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
