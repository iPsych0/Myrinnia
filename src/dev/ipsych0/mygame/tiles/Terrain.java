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
	public static Terrain waterSmallTopLeft = new Terrain(Assets.waterSmallTopLeft, 185);
	public static Terrain waterSmallTopRight = new Terrain(Assets.waterSmallTopRight, 186);
	public static Terrain waterSmallBottomLeft = new Terrain(Assets.waterSmallBottomLeft, 217);
	public static Terrain waterSmallBottomRIght = new Terrain(Assets.waterSmallBottomRight, 218);
	public static Terrain waterTopLeft = new Terrain(Assets.waterTopLeft, 248);
	public static Terrain waterTopMiddle = new Terrain(Assets.waterTopMiddle, 249);
	public static Terrain waterTopRight = new Terrain(Assets.waterTopRight, 250);
	public static Terrain waterMiddleLeft = new Terrain(Assets.waterMiddleLeft, 280);
	public static Terrain waterMiddleMiddle = new Terrain(Assets.waterMiddleMiddle, 281);
	public static Terrain waterMiddleRight = new Terrain(Assets.waterMiddleRight, 282);
	public static Terrain waterBottomLeft = new Terrain(Assets.waterBottomLeft, 312);
	public static Terrain waterBottomMiddle = new Terrain(Assets.waterBottomMiddle, 313);
	public static Terrain waterBottomRight = new Terrain(Assets.waterBottomRight, 314);
	public static Terrain waterFlow1 = new Terrain(Assets.waterFlow1, 344);
	public static Terrain waterFlow2 = new Terrain(Assets.waterFlow2, 345);
	public static Terrain waterFlow3 = new Terrain(Assets.waterFlow3, 346);
	public static Terrain invisible = new Terrain(Assets.invisible, 836);
	

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
			case 346:
				return true;
			case 248:
				return true;
			case 249:
				return true;
			case 250:
				return true;
			case 280:
				return true;
			case 281:
				return true;
			case 282:
				return true;
			case 312:
				return true;
			case 313:
				return true;
			case 314:
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
			case 217:
				return true;
			case 218:
				return true;
				
		default: return false;
		}
	}
	
	public int getId(){
		return id;
	}

}