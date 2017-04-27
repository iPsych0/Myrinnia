package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import dev.ipsych0.mygame.gfx.Assets;

public class Terrain {
	
	public static Terrain[] terrain = new Terrain[1024];
	
	// First try-out Terrain
	
	public static Terrain blackTile = new Terrain(Assets.black, 28);
	
	// House Terrain
	public static Terrain greenHouseRoof = new Terrain(Assets.greenHouseRoof, 409);
	public static Terrain greenHouseWall = new Terrain(Assets.greenHouseWall, 410);
	public static Terrain greenHouseEntrance = new Terrain(Assets.greenHouseEntrance, 411);

	// Terrain
	public static Terrain waterSmallTopLeft = new Terrain(Assets.waterSmallTopLeft, 85);
	public static Terrain waterSmallTopRight = new Terrain(Assets.waterSmallTopRight, 86);
	public static Terrain waterSmallBottomLeft = new Terrain(Assets.waterSmallBottomLeft, 117);
	public static Terrain waterSmallBottomRIght = new Terrain(Assets.waterSmallBottomRight, 118);
	public static Terrain waterTopLeft = new Terrain(Assets.waterTopLeft, 148);
	public static Terrain waterTopMiddle = new Terrain(Assets.waterTopMiddle, 149);
	public static Terrain waterTopRight = new Terrain(Assets.waterTopRight, 150);
	public static Terrain waterMiddleLeft = new Terrain(Assets.waterMiddleLeft, 180);
	public static Terrain waterMiddleMiddle = new Terrain(Assets.waterMiddleMiddle, 181);
	public static Terrain waterMiddleRight = new Terrain(Assets.waterMiddleRight, 182);
	public static Terrain waterBottomLeft = new Terrain(Assets.waterBottomLeft, 212);
	public static Terrain waterBottomMiddle = new Terrain(Assets.waterBottomMiddle, 213);
	public static Terrain waterBottomRight = new Terrain(Assets.waterBottomRight, 214);
	public static Terrain waterFlow1 = new Terrain(Assets.waterFlow1, 244);
	public static Terrain waterFlow2 = new Terrain(Assets.waterFlow2, 245);
	public static Terrain waterFlow3 = new Terrain(Assets.waterFlow3, 246);
	
	public static Terrain sandWaterSmallTopLeft = new Terrain(Assets.sandWaterSmallTopLeft, 271);
	public static Terrain sandWaterSmallTopRight = new Terrain(Assets.sandWaterSmallTopRight, 272);
	public static Terrain sandWaterSmallBottomLeft = new Terrain(Assets.sandWaterSmallBottomLeft, 303);
	public static Terrain sandWaterSmallBottomRight = new Terrain(Assets.sandWaterSmallBottomRight, 304);
	public static Terrain sandWaterTopLeft = new Terrain(Assets.sandWaterTopLeft, 334);
	public static Terrain sandWaterTopMiddle = new Terrain(Assets.sandWaterTopMiddle, 335);
	public static Terrain sandWaterTopRight = new Terrain(Assets.sandWaterTopRight, 336);
	public static Terrain sandWaterMiddleLeft = new Terrain(Assets.sandWaterMiddleLeft, 366);
	public static Terrain sandWaterMiddleMiddle = new Terrain(Assets.sandWaterMiddleMiddle, 367);
	public static Terrain sandWaterMiddleRight = new Terrain(Assets.sandWaterMiddleRight, 368);
	public static Terrain sandWaterBottomLeft = new Terrain(Assets.sandWaterBottomLeft, 398);
	public static Terrain sandWaterBottomMiddle = new Terrain(Assets.sandWaterBottomMiddle, 399);
	public static Terrain sandWaterBottomRight = new Terrain(Assets.sandWaterBottomRight, 400);
	
	public static Terrain lavaSmallTopLeft = new Terrain(Assets.lavaSmallTopLeft, 73);
	public static Terrain lavaSmallTopRight = new Terrain(Assets.lavaSmallTopRight, 74);
	public static Terrain lavaSmallBottomLeft = new Terrain(Assets.lavaSmallBottomLeft, 105);
	public static Terrain lavaSmallBottomRight = new Terrain(Assets.lavaSmallBottomRight, 106);
	public static Terrain lavaTopLeft = new Terrain(Assets.lavaTopLeft, 136);
	public static Terrain lavaTopMiddle = new Terrain(Assets.lavaTopMiddle, 137);
	public static Terrain lavaTopRight = new Terrain(Assets.lavaTopRight, 138);
	public static Terrain lavaMiddleLeft = new Terrain(Assets.lavaMiddleLeft, 168);
	public static Terrain lavaMiddleMiddle = new Terrain(Assets.lavaMiddleMiddle, 169);
	public static Terrain lavaMiddleRight = new Terrain(Assets.lavaMiddleRight, 170);
	public static Terrain lavaBottomLeft = new Terrain(Assets.lavaBottomLeft, 200);
	public static Terrain lavaBottomMiddle = new Terrain(Assets.lavaBottomMiddle, 201);
	public static Terrain lavaBottomRight = new Terrain(Assets.lavaBottomRight, 202);
	public static Terrain lavaFlow1 = new Terrain(Assets.lavaFlow1, 232);
	public static Terrain lavaFlow2 = new Terrain(Assets.lavaFlow2, 233);
	public static Terrain lavaFlow3 = new Terrain(Assets.lavaFlow3, 234);
	
	public static Terrain holeSmallTopLeft = new Terrain(Assets.holeSmallTopLeft, 76);
	public static Terrain holeSmallTopRight = new Terrain(Assets.holeSmallTopRight, 77);
	public static Terrain holeSmallBottomLeft = new Terrain(Assets.holeSmallBottomLeft, 108);
	public static Terrain holeSmallBottomRight = new Terrain(Assets.holeSmallBottomRight, 109);
	public static Terrain holeTopLeft = new Terrain(Assets.holeTopLeft, 139);
	public static Terrain holeTopMiddle = new Terrain(Assets.holeTopMiddle, 140);
	public static Terrain holeTopRight = new Terrain(Assets.holeTopRight, 141);
	public static Terrain holeMiddleLeft = new Terrain(Assets.holeMiddleLeft, 171);
	public static Terrain holeMiddleMiddle = new Terrain(Assets.holeMiddleMiddle, 172);
	public static Terrain holeMiddleRight = new Terrain(Assets.holeMiddleRight, 173);
	public static Terrain holeBottomLeft = new Terrain(Assets.holeBottomLeft, 203);
	public static Terrain holeBottomMiddle = new Terrain(Assets.holeBottomMiddle, 204);
	public static Terrain holeBottomRight = new Terrain(Assets.holeBottomRight, 205);
	
	public static Terrain transDirtTopLeft = new Terrain(Assets.transDirtTopLeft, 121);
	public static Terrain transDirtTopMiddle = new Terrain(Assets.transDirtTopMiddle, 122);
	public static Terrain transDirtTopRight = new Terrain(Assets.transDirtTopRight, 123);
	public static Terrain transDirtMiddleLeft = new Terrain(Assets.transDirtMiddleLeft, 153);
	public static Terrain transDirtMiddleMiddle = new Terrain(Assets.transDirtMiddleMiddle, 154);
	public static Terrain transDirtMiddleRight = new Terrain(Assets.transDirtMiddleRight, 155);
	public static Terrain transDirtBottomLeft = new Terrain(Assets.transDirtBottomLeft, 185);
	public static Terrain transDirtBottomMiddle = new Terrain(Assets.transDirtBottomMiddle, 186);
	public static Terrain transDirtBottomRight = new Terrain(Assets.transDirtBottomRight, 187);
	public static Terrain transDirtSmallTopLeft = new Terrain(Assets.transDirtSmallTopLeft, 58);
	public static Terrain transDirtSmallTopRight = new Terrain(Assets.transDirtSmallTopRight, 59);
	public static Terrain transDirtSmallBottomLeft = new Terrain(Assets.transDirtSmallBottomLeft, 90);
	public static Terrain transDirtSmallBottomRight = new Terrain(Assets.transDirtSmallBottomRight, 91);
	
	public static Terrain darkDirtTopLeft = new Terrain(Assets.darkDirtTopLeft, 124);
	public static Terrain darkDirtTopMiddle = new Terrain(Assets.darkDirtTopMiddle, 125);
	public static Terrain darkDirtTopRight = new Terrain(Assets.darkDirtTopRight, 126);
	public static Terrain darkDirtMiddleLeft = new Terrain(Assets.darkDirtMiddleLeft, 156);
	public static Terrain darkDirtMiddleMiddle = new Terrain(Assets.darkDirtMiddleMiddle, 157);
	public static Terrain darkDirtMiddleRight = new Terrain(Assets.darkDirtMiddleRight, 158);
	public static Terrain darkDirtBottomLeft = new Terrain(Assets.darkDirtBottomLeft, 188);
	public static Terrain darkDirtBottomMiddle = new Terrain(Assets.darkDirtBottomMiddle, 189);
	public static Terrain darkDirtBottomRight = new Terrain(Assets.darkDirtBottomRight, 190);
	public static Terrain darkDirtEffect1 = new Terrain(Assets.darkDirtEffect1, 220);
	public static Terrain darkDirtEffect2 = new Terrain(Assets.darkDirtEffect2, 221);
	public static Terrain darkDirtEffect3 = new Terrain(Assets.darkDirtEffect3, 222);
	public static Terrain darkDirtSmallTopLeft = new Terrain(Assets.darkDirtSmallTopLeft, 61);
	public static Terrain darkDirtSmallTopRight = new Terrain(Assets.darkDirtSmallTopRight, 62);
	public static Terrain darkDirtSmallBottomLeft = new Terrain(Assets.darkDirtSmallBottomLeft, 93);
	public static Terrain darkDirtSmallBottomRight = new Terrain(Assets.darkDirtSmallBottomRight, 94);


	public static Terrain greyDirtTopLeft = new Terrain(Assets.greyDirtTopLeft, 133);
	public static Terrain greyDirtTopMiddle = new Terrain(Assets.greyDirtTopMiddle, 134);
	public static Terrain greyDirtTopRight = new Terrain(Assets.greyDirtTopRight, 135);
	public static Terrain greyDirtMiddleLeft = new Terrain(Assets.greyDirtMiddleLeft, 165);
	public static Terrain greyDirtMiddleMiddle = new Terrain(Assets.greyDirtMiddleMiddle, 166);
	public static Terrain greyDirtMiddleRight = new Terrain(Assets.greyDirtMiddleRight, 167);
	public static Terrain greyDirtBottomLeft = new Terrain(Assets.greyDirtBottomLeft, 197);
	public static Terrain greyDirtBottomMiddle = new Terrain(Assets.greyDirtBottomMiddle, 198);
	public static Terrain greyDirtBottomRight = new Terrain(Assets.greyDirtBottomRight, 199);
	public static Terrain greyDirtEffect1 = new Terrain(Assets.greyDirtEffect1, 229);
	public static Terrain greyDirtEffect2 = new Terrain(Assets.greyDirtEffect2, 230);
	public static Terrain greyDirtEffect3 = new Terrain(Assets.greyDirtEffect3, 231);
	public static Terrain greyDirtSmallTopLeft = new Terrain(Assets.greyDirtSmallTopLeft, 70);
	public static Terrain greyDirtSmallTopRight = new Terrain(Assets.greyDirtSmallTopRight, 71);
	public static Terrain greyDirtSmallBottomLeft = new Terrain(Assets.greyDirtSmallBottomLeft, 102);
	public static Terrain greyDirtSmallBottomRight = new Terrain(Assets.greyDirtSmallBottomRight, 103);


	public static Terrain redDirtTopLeft = new Terrain(Assets.redDirtTopLeft, 127);
	public static Terrain redDirtTopMiddle = new Terrain(Assets.redDirtTopMiddle, 128);
	public static Terrain redDirtTopRight = new Terrain(Assets.redDirtTopRight, 129);
	public static Terrain redDirtMiddleLeft = new Terrain(Assets.redDirtMiddleLeft, 159);
	public static Terrain redDirtMiddleMiddle = new Terrain(Assets.redDirtMiddleMiddle, 160);
	public static Terrain redDirtMiddleRight = new Terrain(Assets.redDirtMiddleRight, 161);
	public static Terrain redDirtBottomLeft = new Terrain(Assets.redDirtBottomLeft, 191);
	public static Terrain redDirtBottomMiddle = new Terrain(Assets.redDirtBottomMiddle, 192);
	public static Terrain redDirtBottomRight = new Terrain(Assets.redDirtBottomRight, 193);
	public static Terrain redDirtEffect1 = new Terrain(Assets.redDirtEffect1, 223);
	public static Terrain redDirtEffect2 = new Terrain(Assets.redDirtEffect2, 224);
	public static Terrain redDirtEffect3 = new Terrain(Assets.redDirtEffect3, 225);
	public static Terrain redDirtSmallTopLeft = new Terrain(Assets.redDirtSmallTopLeft, 64);
	public static Terrain redDirtSmallTopRight = new Terrain(Assets.redDirtSmallTopRight, 65);
	public static Terrain redDirtSmallBottomLeft = new Terrain(Assets.redDirtSmallBottomLeft, 96);
	public static Terrain redDirtSmallBottomRight = new Terrain(Assets.redDirtSmallBottomRight, 97);

	
	public static Terrain darkGrassPatch1 = new Terrain(Assets.darkGrassPatch1, 417);
	public static Terrain darkGrassPatch2 = new Terrain(Assets.darkGrassPatch2, 416);
	public static Terrain darkGrassPatch3 = new Terrain(Assets.darkGrassPatch3, 415);
	public static Terrain invisible = new Terrain(Assets.invisible, 736);
	

	//CLASS
	
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Terrain(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		
		terrain[id] = this;
		
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
		case 205:
			return true;
		case 204:
			return true;
		case 203:
			return true;
		case 173:
			return true;
		case 172:
			return true;
		case 171:
			return true;
		case 141:
			return true;
		case 140:
			return true;
		case 139:
			return true;
		case 109:
			return true;
		case 108:
			return true;
		case 77:
			return true;
		case 76:
			return true;
		case 400:
			return true;
		case 399:
			return true;
		case 398:
			return true;
		case 368:
			return true;
		case 367:
			return true;
		case 366:
			return true;
		case 336:
			return true;
		case 335:
			return true;
		case 334:
			return true;
		case 304:
			return true;
		case 303:
			return true;
		case 272:
			return true;
		case 271:
			return true;
		case 234:
			return true;
		case 233:
			return true;
		case 232:
			return true;
		case 202:
			return true;
		case 201:
			return true;
		case 200:
			return true;
		case 170:
			return true;
		case 169:
			return true;
		case 168:
			return true;
		case 136:
			return true;
		case 137:
			return true;
		case 138:
			return true;
		case 105:
			return true;
		case 106:
			return true;
		case 74:
			return true;
		case 73:
			return true;
		case 148:
			return true;
		case 149:
			return true;
		case 150:
			return true;
		case 180:
			return true;
		case 181:
			return true;
		case 182:
			return true;
		case 212:
			return true;
		case 213:
			return true;
		case 214:
			return true;
		case 244:
			return true;
		case 245:
			return true;
		case 246:
			return true;
		case 85:
			return true;
		case 86:
			return true;
		case 117:
			return true;
		case 118:
			return true;
			
	default: return false;
		}
	}
	
	public int getId(){
		return id;
	}

}