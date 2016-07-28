package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.gfx.Assets;

public class Tiles {
	
	public static Tiles[] tiles = new Tiles[512];
	
	// First try-out tiles
	
	public static Tiles rockTile = new Tiles(Assets.stone, 402);
	public static Tiles caveTile = new Tiles(Assets.cave, 404);
	public static Tiles iceTile = new Tiles(Assets.ice, 405);
	public static Tiles snowTile = new Tiles(Assets.snow, 407);
	public static Tiles blackTile = new Tiles(Assets.black, 408);
	
	// House tiles
	public static Tiles greenHouseRoof = new Tiles(Assets.greenHouseRoof, 409);
	public static Tiles greenHouseWall = new Tiles(Assets.greenHouseWall, 410);
	public static Tiles greenHouseEntrance = new Tiles(Assets.greenHouseEntrance, 411);
	
	// Grass and Path tiles
	public static Tiles grassTile = new Tiles(Assets.grassOnly, 100);
	public static Tiles path = new Tiles(Assets.path, 101);
	public static Tiles pathGrassTopLeftTile = new Tiles(Assets.pathGrassTopLeft, 102);
	public static Tiles pathGrassTopMiddleTile = new Tiles(Assets.pathGrassTopMiddle, 103);
	public static Tiles pathGrassTopRightTile = new Tiles(Assets.pathGrassTopRight, 104);
	public static Tiles pathGrassMiddleLeftTile = new Tiles(Assets.pathGrassMiddleLeft, 105);
	public static Tiles pathGrassMiddleRightTile = new Tiles(Assets.pathGrassMiddleRight, 106);
	public static Tiles pathGrassDownLeftTile = new Tiles(Assets.pathGrassDownLeft, 107);
	public static Tiles pathGrassDownMiddleTile = new Tiles(Assets.pathGrassDownMiddle, 108);
	public static Tiles pathGrassDownRightTile = new Tiles(Assets.pathGrassDownRight, 109);
	
	public static Tiles sandTile = new Tiles(Assets.sand, 110);
	public static Tiles sandGrassTopLeftTile = new Tiles(Assets.sandGrassTopLeft, 111);
	public static Tiles sandGrassTopMiddleTile = new Tiles(Assets.sandGrassTopMiddle, 112);
	public static Tiles sandGrassTopRightTile = new Tiles(Assets.sandGrassTopRight, 113);
	public static Tiles sandGrassMiddleLeftTile = new Tiles(Assets.sandGrassMiddleLeft, 114);
	public static Tiles sandGrassMiddleRightTile = new Tiles(Assets.sandGrassMiddleRight, 115);
	public static Tiles sandGrassDownLeftTile = new Tiles(Assets.sandGrassDownLeft, 116);
	public static Tiles sandGrassDownMiddleTile = new Tiles(Assets.sandGrassDownMiddle, 117);
	public static Tiles sandGrassDownRightTile = new Tiles(Assets.sandGrassDownRight, 118);
	
	public static Tiles waterTile = new Tiles(Assets.water, 120);
	public static Tiles waterSandTopLeftTile = new Tiles(Assets.waterSandTopLeft, 121);
	public static Tiles waterSandTopMiddleTile = new Tiles(Assets.waterSandTopMiddle, 122);
	public static Tiles waterSandTopRightTile = new Tiles(Assets.waterSandTopRight, 123);
	public static Tiles waterSandMiddleLeftTile = new Tiles(Assets.waterSandMiddleLeft, 124);
	public static Tiles waterSandMiddleRightTile = new Tiles(Assets.waterSandMiddleRight, 125);
	public static Tiles waterSandDownLeftTile = new Tiles(Assets.waterSandDownLeft, 126);
	public static Tiles waterSandDownMiddleTile = new Tiles(Assets.waterSandDownMiddle, 127);
	public static Tiles waterSandDownRightTile = new Tiles(Assets.waterSandDownRight, 128);
	
	public static Tiles dirt = new Tiles(Assets.dirt, 130);
	public static Tiles dirtSandTopLeft = new Tiles(Assets.dirtSandTopLeft, 131);
	public static Tiles dirtSandTopMiddle = new Tiles(Assets.dirtSandTopMiddle, 132);
	public static Tiles dirtSandTopRight = new Tiles(Assets.dirtSandTopRight, 133);
	public static Tiles dirtSandMiddleLeft = new Tiles(Assets.dirtSandMiddleLeft, 134);
	public static Tiles dirtSandMiddleRight = new Tiles(Assets.dirtSandMiddleRight, 135);
	public static Tiles dirtSandDownLeft = new Tiles(Assets.dirtSandDownLeft, 136);
	public static Tiles dirtSandDownMiddle = new Tiles(Assets.dirtSandDownMiddle, 137);
	public static Tiles dirtSandDownRight = new Tiles(Assets.dirtSandDownRight, 138);
	
	public static Tiles dirtGrassTopLeft = new Tiles(Assets.dirtGrassTopLeft, 141);
	public static Tiles dirtGrassTopMiddle = new Tiles(Assets.dirtGrassTopMiddle, 142);
	public static Tiles dirtGrassTopRight = new Tiles(Assets.dirtGrassTopRight, 143);
	public static Tiles dirtGrassmiddleLeft = new Tiles(Assets.dirtGrassmiddleLeft, 144);
	public static Tiles dirtGrassMiddleRight = new Tiles(Assets.dirtGrassMiddleRight, 145);
	public static Tiles dirtGrassDownLeft = new Tiles(Assets.dirtGrassDownLeft, 146);
	public static Tiles dirtGrassDownMiddle = new Tiles(Assets.dirtGrassDownMiddle, 147);
	public static Tiles dirtGrassDownRight = new Tiles(Assets.dirtGrassDownRight, 148);

	public static Tiles pathDirtTopLeft = new Tiles(Assets.pathDirtTopLeft, 151);
	public static Tiles pathDirtTopMiddle = new Tiles(Assets.pathDirtTopMiddle, 152);
	public static Tiles pathDirtTopRight = new Tiles(Assets.pathDirtTopRight, 153);
	public static Tiles pathDirtMiddleLeft = new Tiles(Assets.pathDirtMiddleLeft, 154);
	public static Tiles pathDirtMiddleRight = new Tiles(Assets.pathDirtMiddleRight, 155);
	public static Tiles pathDirtDownLeft = new Tiles(Assets.pathDirtDownLeft, 156);
	public static Tiles pathDirtDownMiddle = new Tiles(Assets.pathDirtDownMiddle, 157);
	public static Tiles pathDirtDownRight = new Tiles(Assets.pathDirtDownRight, 158);

	public static Tiles lavaTile = new Tiles(Assets.lava, 160);
	public static Tiles lavaPathTopLeft = new Tiles(Assets.lavaPathTopLeft, 161);
	public static Tiles lavaPathTopMiddle = new Tiles(Assets.lavaPathTopMiddle, 162);
	public static Tiles lavaPathTopRight = new Tiles(Assets.lavaPathTopRight, 163);
	public static Tiles lavaPathMiddleLeft = new Tiles(Assets.lavaPathMiddleLeft, 164);
	public static Tiles lavaPathMiddleRight = new Tiles(Assets.lavaPathMiddleRight, 165);
	public static Tiles lavaPathDownLeft = new Tiles(Assets.lavaPathDownLeft, 166);
	public static Tiles lavaPathDownMiddle = new Tiles(Assets.lavaPathDownMiddle, 167);
	public static Tiles lavaPathDownRight = new Tiles(Assets.lavaPathDownRight, 168);
	
	public static Tiles lavaSandTopLeft = new Tiles(Assets.lavaSandTopLeft, 171);
	public static Tiles lavaSandTopMiddle = new Tiles(Assets.lavaSandTopMiddle, 172);
	public static Tiles lavaSandTopRight = new Tiles(Assets.lavaSandTopRight, 173);
	public static Tiles lavaSandMiddleLeft = new Tiles(Assets.lavaSandMiddleLeft, 174);
	public static Tiles lavaSandMiddleRight = new Tiles(Assets.lavaSandMiddleRight, 175);
	public static Tiles lavaSandDownLeft = new Tiles(Assets.lavaSandDownLeft, 176);
	public static Tiles lavaSandDownMiddle = new Tiles(Assets.lavaSandDownMiddle, 177);
	public static Tiles lavaSandDownRight = new Tiles(Assets.lavaSandDownRight, 178);
	
	public static Tiles waterDirtTopLeft = new Tiles(Assets.waterDirtTopLeft, 181);
	public static Tiles waterDirtTopMiddle = new Tiles(Assets.waterDirtTopMiddle, 182);
	public static Tiles waterDirtTopRight = new Tiles(Assets.waterDirtTopRight, 183);
	public static Tiles waterDirtMiddleLeft = new Tiles(Assets.waterDirtMiddleLeft, 184);
	public static Tiles waterDirtMiddleRight = new Tiles(Assets.waterDirtMiddleRight, 185);
	public static Tiles waterDirtDownLeft = new Tiles(Assets.waterDirtDownLeft, 186);
	public static Tiles waterDirtDownMiddle = new Tiles(Assets.waterDirtDownMiddle, 187);
	public static Tiles waterDirtDownRight = new Tiles(Assets.waterDirtDownRight, 188);
	
	
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
		switch(id){
			case 402:
				return true;
			case 408:
				return true;
			case 409:
				return true;
			case 410:
				return true;
			case 120:
				return true;
			case 121:
				return true;
			case 122:
				return true;
			case 123:
				return true;
			case 124:
				return true;
			case 125:
				return true;
			case 126:
				return true;
			case 127:
				return true;
			case 128:
				return true;
			case 160:
				return true;
			case 161:
				return true;
			case 162:
				return true;
			case 163:
				return true;
			case 164:
				return true;
			case 165:
				return true;
			case 166:
				return true;
			case 167:
				return true;
			case 168:
				return true;
			case 171:
				return true;
			case 172:
				return true;
			case 173:
				return true;
			case 174:
				return true;
			case 175:
				return true;
			case 176:
				return true;
			case 177:
				return true;
			case 178:
				return true;
			case 181:
				return true;
			case 182:
				return true;
			case 183:
				return true;
			case 184:
				return true;
			case 185:
				return true;
			case 186:
				return true;
			case 187:
				return true;
			case 188:
				return true;
				
		default: return false;
		}
	}
	
	public int getId(){
		return id;
	}

}
