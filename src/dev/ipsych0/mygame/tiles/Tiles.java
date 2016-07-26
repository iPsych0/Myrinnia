package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiles {
	
	public static Tiles[] tiles = new Tiles[512];
	
	// First try-out tiles
	
	public static Tiles lavaTile = new LavaTile(400);
	public static Tiles dirtTile = new DirtTile(401);
	public static Tiles rockTile = new RockTile(402);
	public static Tiles waterTile = new WaterTile(403);
	public static Tiles caveTile = new CaveTile(404);
	public static Tiles iceTile = new IceTile(405);
	public static Tiles sandTile = new SandTile(406);
	public static Tiles snowTile = new SnowTile(407);
	public static Tiles blackTile = new BlackTile(408);
	
	// House tiles
	public static Tiles greenHouseRoof = new GreenHouseRoof(409);
	public static Tiles greenHouseWall = new GreenHouseWall(410);
	public static Tiles greenHouseEntrance = new GreenHouseEntrance(411);
	
	// Grass and Path tiles
	public static Tiles grassTile = new GrassTile(100);
	public static Tiles pebblegrassTopLeftTile = new PebblegrassTopLeftTile(101);
	public static Tiles pebblegrassTopMiddleTile = new PebblegrassTopMiddleTile(102);
	public static Tiles PebblegrassTopRightTile = new PebblegrassTopRightTile(103);
	public static Tiles pebblegrassMiddleLeftTile = new PebblegrassMiddleLeftTile(104);
	public static Tiles pebblegrassMiddleMiddleTile = new PebblegrassMiddleMiddleTile(105);
	public static Tiles pebblegrassMiddleRightTile = new PebblegrassMiddleRightTile(106);
	public static Tiles pebblegrassDownLeftTile = new PebblegrassDownLeftTile(107);
	public static Tiles pebblegrassDownMiddleTile = new PebblegrassDownMiddleTile(108);
	public static Tiles pebblegrassDownRightTile = new PebblegrassDownRightTile(109);
	
	
	
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
