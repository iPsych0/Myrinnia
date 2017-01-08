package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import dev.ipsych0.mygame.gfx.Assets;

public class Terrain {
	
	public static Terrain[] terrain = new Terrain[1024];
	
	// First try-out Terrain
	
	public static Terrain rockTile = new Terrain(Assets.stone, 104);
	public static Terrain caveTile = new Terrain(Assets.cave, 112);
	public static Terrain iceTile = new Terrain(Assets.ice, 116);
	public static Terrain snowTile = new Terrain(Assets.snow, 124);
	public static Terrain blackTile = new Terrain(Assets.black, 128);
	
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
			case 246:
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
			case 247:
				return true;
			case 60:
				return true;
			case 61:
				return true;
			case 62:
				return true;
			case 63:
				return true;
			case 64:
				return true;
			case 65:
				return true;
			case 66:
				return true;
			case 67:
				return true;
			case 68:
				return true;
			case 71:
				return true;
			case 72:
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
			case 183:
				return true;
			case 184:
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