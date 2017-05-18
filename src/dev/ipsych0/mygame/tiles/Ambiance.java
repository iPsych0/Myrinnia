package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import dev.ipsych0.mygame.gfx.Assets;

public class Ambiance {
	
	public static Ambiance[] ambiance = new Ambiance[2048];
	
	public static Ambiance blackTile = new Ambiance(Assets.black, 28);

	// Ambience
	public static Ambiance invisible = new Ambiance(Assets.invisible, 736);
	public static Ambiance sparkleTile = new Ambiance(Assets.sparkleTile, 649);
	public static Ambiance redMushroom = new Ambiance(Assets.redMushroom, 1184);
	public static Ambiance blueMushroom = new Ambiance(Assets.blueMushroom, 1168);
	public static Ambiance smallRedRock = new Ambiance(Assets.smallRedRocks, 664);
	

	//CLASS
	
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Ambiance(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		
		ambiance[id] = this;
		
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
			case 1184:
				return true;
			case 1168:
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
			case 344:
				return true;
			case 345:
				return true;
			case 347:
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