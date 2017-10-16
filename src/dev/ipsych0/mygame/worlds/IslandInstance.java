package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.tiles.Tiles;

public class IslandInstance extends World {
	
	private Player player;
	private Rectangle exit;

	public IslandInstance(Handler handler, Player player, String path, int worldID) {
		super(handler);
		
		this.worldID = worldID;
		this.player = player;
		
		mapLoader = new MapLoader();
		
		width = mapLoader.getMapWidth(path);
		height = mapLoader.getMapHeight(path);
		
		loadWorld(path);
		
		entityManager.addEntity(new Lorraine(handler, 732, 440));
		
		entityManager.addEntity(new Tree(handler, 360, 128));
		entityManager.addEntity(new Tree(handler, 328, 128));
		entityManager.addEntity(new Tree(handler, 296, 192));
		entityManager.addEntity(new Tree(handler, 296, 160));
		
		entityManager.addEntity(new Rock(handler, 448, 576));
		
		entityManager.addEntity(new TeleportShrine2(handler, 200, 200));
		entityManager.addEntity(new TeleportShrine1(handler, 200, 168));
		
		entityManager.addEntity(new Whirlpool(handler, 112, 928));
		
		exit = new Rectangle(6016, 6192, 32, 32);
	}

	@Override
	public void tick() {
		if(handler.getWorld() == this){
			itemManager.tick();
			entityManager.tick();
			sparkles.tick();
			inventory.tick();
			equipment.tick();
			miniMap.tick();
			craftingUI.tick();
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(exit)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(2));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(5056);
				handler.getPlayer().setY(5460);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
				handler.getPlayer().getChatWindow().sendMessage("X = " + getEntityManager().getPlayer().getX() + " and Y = " + getEntityManager().getPlayer().getY());
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(handler.getWorld() == this){
			// Set variables for rendering only the tiles that show on screen
			int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tiles.TILEWIDTH);
			int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tiles.TILEWIDTH + 1);
			int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tiles.TILEHEIGHT);
			int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tiles.TILEHEIGHT + 1);
			
			// Render the tiles
			for (int i = 0; i < file.length; i++) {
				for(int y = yStart; y < yEnd; y++){
					for(int x = xStart; x < xEnd; x++){
						getTile(i,x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
								(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
					}
				}
			}
			
			// Items
			
			itemManager.render(g);
			
			// Entities
			entityManager.render(g);
			
			inventory.render(g);
			equipment.render(g);
			
			// MiniMap
			miniMap.render(g);
			craftingUI.render(g);
			
			g.drawRect((int) (exit.x - handler.getGameCamera().getxOffset()), (int) (exit.y - handler.getGameCamera().getyOffset()), 32, 350);
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
}
