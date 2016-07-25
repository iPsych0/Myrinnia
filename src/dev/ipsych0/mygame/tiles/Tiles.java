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
	public static Tiles pathupTile = new PathupTile(113);
	public static Tiles pathup2Tile = new Pathup2Tile(114);
	public static Tiles pebblegrassTopLeftTile = new PebblegrassTopLeftTile(120);
	public static Tiles pebblegrassTopMiddleTile = new PebblegrassTopMiddleTile(121);
	public static Tiles PebblegrassTopRightTile = new PebblegrassTopRightTile(122);
	public static Tiles pebblegrassMiddleLeftTile = new PebblegrassMiddleLeftTile(123);
	public static Tiles pebblegrassMiddleMiddleTile = new PebblegrassMiddleMiddleTile(124);
	public static Tiles pebblegrassMiddleRightTile = new PebblegrassMiddleRightTile(125);
	public static Tiles pebblegrassDownLeftTile = new PebblegrassDownLeftTile(126);
	public static Tiles pebblegrassDownMiddleTile = new PebblegrassDownMiddleTile(127);
	public static Tiles pebblegrassDownRightTile = new PebblegrassDownRightTile(128);
	public static Tiles pebblegrassSmallTile = new PebblegrassSmallTile(129);
	public static Tiles pebblegrassBigTile = new PebblegrassBigTile(130);
	public static Tiles grassonlyTile = new GrassonlyTile(131);  // works for all grasstiles
	public static Tiles hillgrassTopLeftTile = new HillgrassTopLeftTile(132);
	public static Tiles hillgrassTopMiddleTile = new HillgrassTopMiddleTile(133);
	public static Tiles hillgrassTopRightTile = new HillgrassTopRightTile(134);
	public static Tiles hillgrassMiddleLeftTile = new HillgrassMiddleLeftTile(135);
	public static Tiles hillgrassMiddleMiddleTile = new HillgrassMiddleMiddleTile(136);
	public static Tiles hillgrassMiddleRightTile = new HillgrassMiddleRightTile(137);
	public static Tiles hillgrassDownLeftTile = new HillgrassDownLeftTile(138); 
	public static Tiles hillgrassDownMiddleTile = new HillgrassDownMiddleTile(139);
	public static Tiles hillgrassDownRightTile = new HillgrassDownRightTile(140);
	public static Tiles hillgrassOutcropTile = new HillgrassOutcropTile(141);
	public static Tiles hillgrassFourwayTile = new HillgrassFourwayTile(142);
	
	
	
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
