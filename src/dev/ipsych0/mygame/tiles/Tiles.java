package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiles {
	
	public static Tiles[] tiles = new Tiles[512];
	
	// First try-out tiles
	
	//public static Tiles lavaTile = new LavaTile(400);
	//public static Tiles dirtTile = new DirtTile(401);
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
	public static Tiles path = new PathTile(101);
	public static Tiles pathGrassTopLeftTile = new PathGrassTopLeftTile(102);
	public static Tiles pathGrassTopMiddleTile = new PathGrassTopMiddleTile(103);
	public static Tiles pathGrassTopRightTile = new PathGrassTopRightTile(104);
	public static Tiles pathGrassMiddleLeftTile = new PathGrassMiddleLeftTile(105);
	public static Tiles pathGrassMiddleRightTile = new PathGrassMiddleRightTile(106);
	public static Tiles pathGrassDownLeftTile = new PathGrassDownLeftTile(107);
	public static Tiles pathGrassDownMiddleTile = new PathGrassDownMiddleTile(108);
	public static Tiles pathGrassDownRightTile = new PathGrassDownRightTile(109);
	
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
	
	public static Tiles dirt = new DirtTile(130);
	public static Tiles dirtSandTopLeft = new DirtSandTopLeft(131);
	public static Tiles dirtSandTopMiddle = new DirtSandTopMiddle(132);
	public static Tiles dirtSandTopRight = new DirtSandTopRight(133);
	public static Tiles dirtSandMiddleLeft = new DirtSandMiddleLeft(134);
	public static Tiles dirtSandMiddleRight = new DirtSandMiddleRight(135);
	public static Tiles dirtSandDownLeft = new DirtSandDownLeft(136);
	public static Tiles dirtSandDownMiddle = new DirtSandDownMiddle(137);
	public static Tiles dirtSandDownRight = new DirtSandDownRight(138);
	
	public static Tiles dirtGrassTopLeft = new DirtGrassTopLeft(141);
	public static Tiles dirtGrassTopMiddle = new DirtGrassTopMiddle(142);
	public static Tiles dirtGrassTopRight = new DirtGrassTopRight(143);
	public static Tiles dirtGrassmiddleLeft = new DirtGrassmiddleLeft(144);
	public static Tiles dirtGrassMiddleRight = new DirtGrassMiddleRight(145);
	public static Tiles dirtGrassDownLeft = new DirtGrassDownLeft(146);
	public static Tiles dirtGrassDownMiddle = new DirtGrassDownMiddle(147);
	public static Tiles dirtGrassDownRight = new DirtGrassDownRight(148);

	public static Tiles pathDirtTopLeft = new PathDirtTopLeft(151);
	public static Tiles pathDirtTopMiddle = new PathDirtTopMiddle(152);
	public static Tiles pathDirtTopRight = new PathDirtTopRight(153);
	public static Tiles pathDirtMiddleLeft = new PathDirtMiddleLeft(154);
	public static Tiles pathDirtMiddleRight = new PathDirtMiddleRight(155);
	public static Tiles pathDirtDownLeft = new PathDirtDownLeft(156);
	public static Tiles pathDirtDownMiddle = new PathDirtDownMiddle(157);
	public static Tiles pathDirtDownRight = new PathDirtDownRight(158);

	public static Tiles lavaTile = new LavaTile(160);
	public static Tiles lavaPathTopLeft = new LavaPathTopLeft(161);
	public static Tiles lavaPathTopMiddle = new LavaPathTopMiddle(162);
	public static Tiles lavaPathTopRight = new LavaPathTopRight(163);
	public static Tiles lavaPathMiddleLeft = new LavaPathMiddleLeft(164);
	public static Tiles lavaPathMiddleRight = new LavaPathMiddleRight(165);
	public static Tiles lavaPathDownLeft = new LavaPathDownLeft(166);
	public static Tiles lavaPathDownMiddle = new LavaPathDownMiddle(167);
	public static Tiles lavaPathDownRight = new LavaPathDownRight(168);
	
	public static Tiles lavaSandTopLeft = new LavaSandTopLeft(171);
	public static Tiles lavaSandTopMiddle = new LavaSandTopMiddle(172);
	public static Tiles lavaSandTopRight = new LavaSandTopRight(173);
	public static Tiles lavaSandMiddleLeft = new LavaSandMiddleLeft(174);
	public static Tiles lavaSandMiddleRight = new LavaSandMiddleRight(175);
	public static Tiles lavaSandDownLeft = new LavaSandDownLeft(176);
	public static Tiles lavaSandDownMiddle = new LavaSandDownMiddle(177);
	public static Tiles lavaSandDownRight = new LavaSandDownRight(178);
	
	public static Tiles waterDirtTopLeft = new WaterDirtTopLeft(181);
	public static Tiles waterDirtTopMiddle = new WaterDirtTopMiddle(182);
	public static Tiles waterDirtTopRight = new WaterDirtTopRight(183);
	public static Tiles waterDirtMiddleLeft = new WaterDirtMiddleLeft(184);
	public static Tiles waterDirtMiddleRight = new WaterDirtMiddleRight(185);
	public static Tiles waterDirtDownLeft = new WaterDirtDownLeft(186);
	public static Tiles waterDirtDownMiddle = new WaterDirtDownMiddle(187);
	public static Tiles waterDirtDownRight = new WaterDirtDownRight(188);
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
