package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.gfx.Assets;

public class Tiles {
	
	public static Tiles[] tiles = new Tiles[512];
	
	// First try-out tiles

	public static Tiles blackTile = new Tiles(Assets.black, 28);
	
	// Ground Tiles
	public static Tiles grassTile = new Tiles(Assets.grassOnly, 10);
	public static Tiles path = new Tiles(Assets.path, 12);
	public static Tiles pathGrassTopLeftTile = new Tiles(Assets.pathGrassTopLeft, 1);
	public static Tiles pathGrassTopMiddleTile = new Tiles(Assets.pathGrassTopMiddle, 2);
	public static Tiles pathGrassTopRightTile = new Tiles(Assets.pathGrassTopRight, 3);
	public static Tiles pathGrassMiddleLeftTile = new Tiles(Assets.pathGrassMiddleLeft, 11);
	public static Tiles pathGrassMiddleRightTile = new Tiles(Assets.pathGrassMiddleRight, 13);
	public static Tiles pathGrassDownLeftTile = new Tiles(Assets.pathGrassDownLeft, 21);
	public static Tiles pathGrassDownMiddleTile = new Tiles(Assets.pathGrassDownMiddle, 22);
	public static Tiles pathGrassDownRightTile = new Tiles(Assets.pathGrassDownRight, 23);
	
	public static Tiles sandTile = new Tiles(Assets.sand, 15);
	public static Tiles sandGrassTopLeftTile = new Tiles(Assets.sandGrassTopLeft, 4);
	public static Tiles sandGrassTopMiddleTile = new Tiles(Assets.sandGrassTopMiddle, 5);
	public static Tiles sandGrassTopRightTile = new Tiles(Assets.sandGrassTopRight, 6);
	public static Tiles sandGrassMiddleLeftTile = new Tiles(Assets.sandGrassMiddleLeft, 14);
	public static Tiles sandGrassMiddleRightTile = new Tiles(Assets.sandGrassMiddleRight, 16);
	public static Tiles sandGrassDownLeftTile = new Tiles(Assets.sandGrassDownLeft, 24);
	public static Tiles sandGrassDownMiddleTile = new Tiles(Assets.sandGrassDownMiddle, 25);
	public static Tiles sandGrassDownRightTile = new Tiles(Assets.sandGrassDownRight, 26);
	
	public static Tiles waterTile = new Tiles(Assets.water, 78);
	public static Tiles waterSandTopLeftTile = new Tiles(Assets.waterSandTopLeft, 7);
	public static Tiles waterSandTopMiddleTile = new Tiles(Assets.waterSandTopMiddle, 8);
	public static Tiles waterSandTopRightTile = new Tiles(Assets.waterSandTopRight, 9);
	public static Tiles waterSandMiddleLeftTile = new Tiles(Assets.waterSandMiddleLeft, 17);
	public static Tiles waterSandMiddleRightTile = new Tiles(Assets.waterSandMiddleRight, 19);
	public static Tiles waterSandDownLeftTile = new Tiles(Assets.waterSandDownLeft, 27);
	public static Tiles waterSandDownMiddleTile = new Tiles(Assets.waterSandDownMiddle, 28);
	public static Tiles waterSandDownRightTile = new Tiles(Assets.waterSandDownRight, 29);
	
	public static Tiles dirtGrassTopLeft = new Tiles(Assets.dirtGrassTopLeft, 34);
	public static Tiles dirtGrassTopMiddle = new Tiles(Assets.dirtGrassTopMiddle, 35);
	public static Tiles dirtGrassTopRight = new Tiles(Assets.dirtGrassTopRight, 36);
	public static Tiles dirtGrassmiddleLeft = new Tiles(Assets.dirtGrassmiddleLeft, 44);
	public static Tiles dirtGrassMiddleRight = new Tiles(Assets.dirtGrassMiddleRight, 46);
	public static Tiles dirtGrassDownLeft = new Tiles(Assets.dirtGrassDownLeft, 54);
	public static Tiles dirtGrassDownMiddle = new Tiles(Assets.dirtGrassDownMiddle, 55);
	public static Tiles dirtGrassDownRight = new Tiles(Assets.dirtGrassDownRight, 56);

	public static Tiles pathDirtTopLeft = new Tiles(Assets.pathDirtTopLeft, 37);
	public static Tiles pathDirtTopMiddle = new Tiles(Assets.pathDirtTopMiddle, 38);
	public static Tiles pathDirtTopRight = new Tiles(Assets.pathDirtTopRight, 39);
	public static Tiles pathDirtMiddleLeft = new Tiles(Assets.pathDirtMiddleLeft, 47);
	public static Tiles pathDirtMiddleRight = new Tiles(Assets.pathDirtMiddleRight, 49);
	public static Tiles pathDirtDownLeft = new Tiles(Assets.pathDirtDownLeft, 57);
	public static Tiles pathDirtDownMiddle = new Tiles(Assets.pathDirtDownMiddle, 58);
	public static Tiles pathDirtDownRight = new Tiles(Assets.pathDirtDownRight, 59);
	
	public static Tiles lavaSandTopLeft = new Tiles(Assets.lavaSandTopLeft, 64);
	public static Tiles lavaSandTopMiddle = new Tiles(Assets.lavaSandTopMiddle, 65);
	public static Tiles lavaSandTopRight = new Tiles(Assets.lavaSandTopRight, 66);
	public static Tiles lavaSandMiddleLeft = new Tiles(Assets.lavaSandMiddleLeft, 74);
	public static Tiles lavaSandMiddleRight = new Tiles(Assets.lavaSandMiddleRight, 76);
	public static Tiles lavaSandDownLeft = new Tiles(Assets.lavaSandDownLeft, 84);
	public static Tiles lavaSandDownMiddle = new Tiles(Assets.lavaSandDownMiddle, 85);
	public static Tiles lavaSandDownRight = new Tiles(Assets.lavaSandDownRight, 86);
	
	public static Tiles waterDirtTopLeft = new Tiles(Assets.waterDirtTopLeft, 67);
	public static Tiles waterDirtTopMiddle = new Tiles(Assets.waterDirtTopMiddle, 68);
	public static Tiles waterDirtTopRight = new Tiles(Assets.waterDirtTopRight, 69);
	public static Tiles waterDirtMiddleLeft = new Tiles(Assets.waterDirtMiddleLeft, 77);
	public static Tiles waterDirtMiddleRight = new Tiles(Assets.waterDirtMiddleRight, 79);
	public static Tiles waterDirtDownLeft = new Tiles(Assets.waterDirtDownLeft, 87);
	public static Tiles waterDirtDownMiddle = new Tiles(Assets.waterDirtDownMiddle, 88);
	public static Tiles waterDirtDownRight = new Tiles(Assets.waterDirtDownRight, 89);
	
	public static Tiles darkGrass = new Tiles(Assets.darkGrass, 352);
	public static Tiles darkGrassPatch1 = new Tiles(Assets.darkGrassPatch1, 415);
	public static Tiles darkGrassPatch2 = new Tiles(Assets.darkGrassPatch2, 416);
	public static Tiles darkGrassPatch3 = new Tiles(Assets.darkGrassPatch3, 417);
	
	
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
			case 28:
				return true;
			case 78:
				return true;
			case 72:
				return true;
			case 104:
				return true;
			case 119:
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
