package dev.ipsych0.mygame.mapeditor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.Utils;
import dev.ipsych0.mygame.worlds.World;

public class MiniMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private int x, y, width, height;
	private int[][] tiles;
	private int[][] terrain;
	private String file;
	private boolean tilesLoaded = false;
	public static boolean isOpen = false;
	
	public MiniMap(Handler handler, String path, int x, int y, int width, int height){
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
			float alpha = 0.9f; //draw half transparent
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
			((Graphics2D) g).setComposite(ac);
			
			/*
			 * Om scrollbaar te maken:
			 * TODO: Assets.swampland.getSubimage() iets daar doen en updaten!!!
			 */
			g.drawImage(Assets.swampLand, x, y, Assets.swampLand.getWidth() / 2, Assets.swampLand.getHeight() / 2, null);
			
			
			
			for(Item i : handler.getWorld().getItemManager().getItems()){
				g.setColor(Color.RED);
				g.fillRect((int)(i.getX() / 4) + x, (int)(i.getY() / 4) + y, 3, 3);
			}
			for(Entity e: handler.getWorld().getEntityManager().getEntities()){
				if(!e.isDrawnOnMap()){
					continue;
				}
				if(!e.isNpc()){
					g.setColor(Color.YELLOW);
					g.fillRect((int)(e.getX() / 4) + x, (int)(e.getY() / 4) + y, 3, 3);
				}
				if(e.isNpc()){
					g.setColor(Color.ORANGE);
					g.fillRect((int)(e.getX() / 4) + x, (int)(e.getY() / 4) + y, 3, 3);
				}
				if(e.equals(handler.getWorld().getEntityManager().getPlayer())){
					g.setColor(Color.WHITE);
					g.fillRect((int)(e.getX() / 4) + x, (int)(e.getY() / 4) + y, 3, 3);
				}
			}
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		}
		
	}

}
