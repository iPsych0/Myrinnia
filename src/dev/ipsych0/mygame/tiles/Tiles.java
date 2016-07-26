package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiles {
	
	public static Tiles[] tiles = new Tiles[512];
	
	// First try-out tiles
	
	public static Tiles lavaTile = new LavaTile(400);
	public static Tiles dirtTile = new DirtTile(401);
	public static Tiles rockTile = new RockTile(402);
	//public static Tiles waterTile = new WaterTile(403); kan weg?
	public static Tiles caveTile = new CaveTile(404);
	public static Tiles iceTile = new IceTile(405);
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
	public static Tiles sandTile = new SandTile(110);
	
	public static Tiles sandGrassTopLeftTile = new SandGrassTopLeftTile(111);
	public static Tiles sandGrassTopMiddleTile = new SandGrassTopMiddleTile(112);
	public static Tiles sandGrassTopRightTile = new SandGrassTopRightTile(113);
	public static Tiles sandGrassMiddleLeftTile = new SandGrassMiddleLeftTile(114);
	public static Tiles sandGrassMiddleRightTile = new SandGrassMiddleRightTile(115);
	public static Tiles sandGrassDownLeftTile = new SandGrassDownLeftTile(116);
	public static Tiles sandGrassDownMiddleTile = new SandGrassDownMiddleTile(117);
	public static Tiles sandGrassDownRightTile = new SandGrassDownRightTile(118);
	
	public static Tiles waterTile = new WaterTile(120);
	public static Tiles waterSandTopLeftTile = new WaterSandTopLeftTile(121);
	public static Tiles waterSandTopMiddleTile = new WaterSandTopMiddleTile(122);
	public static Tiles waterSandTopRightTile = new WaterSandTopRightTile(123);
	public static Tiles waterSandMiddleLeftTile = new WaterSandMiddleLeftTile(124);
	public static Tiles waterSandMiddleRightTile = new WaterSandMiddleRightTile(125);
	public static Tiles waterSandDownLeftTile = new WaterSandDownLeftTile(126);
	public static Tiles waterSandDownMiddleTile = new WaterSandDownMiddleTile(127);
	public static Tiles waterSandDownRightTile = new WaterSandDownRightTile(128);
	
	//KK
	
	
	
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
