package dev.ipsych0.mygame.mapeditor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.tiles.Ambiance;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Utils;
import dev.ipsych0.mygame.worlds.World;

public class MiniMap{
	
	private Handler handler;
	private int x, y, width, height;
	private int[][] tiles;
	private int[][] terrain;
	private MapLoader mapLoader;
	private String file;
	private boolean tilesLoaded = false;
	public static boolean isOpen = false;
	
	public MiniMap(Handler handler, String path, int x, int y, int width, int height){
		mapLoader = new MapLoader();
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(isOpen){
			float alpha = 0.75f; //draw half transparent
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
			((Graphics2D) g).setComposite(ac);
			for(int j = 0; j < handler.getWorld().getHeight(); j++){
				for(int i = 0; i < handler.getWorld().getWidth(); i++){
					g.drawImage(handler.getWorld().getTile(i, j).getTexture(), x, y, (int)Tiles.TILEWIDTH * 25 / 100 , (int)Tiles.TILEHEIGHT * 25 / 100 , null);
					g.drawImage(handler.getWorld().getTerrain(i, j).getTexture(), x, y, (int)Tiles.TILEWIDTH * 25 / 100 , (int)Tiles.TILEHEIGHT * 25 / 100 , null);
					if(x == 400){
						x = 0;
					}
					x += 8;
				}
				if( y == 400){
					y = 0;
				}
				y += 8;
			}
			g.setColor(Color.BLACK);
			g.drawRect(8, 8, width, height);
		}
		
	}

}
