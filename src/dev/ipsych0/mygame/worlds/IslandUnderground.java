package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Campfire;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.tiles.Tiles;

public class IslandUnderground extends World {
	
	private Rectangle house1Exit;
	private Rectangle house2Exit;
	private Rectangle house3Exit;
	private Rectangle beachHouse1Exit;
	private Rectangle beachCaveExit;
	private Rectangle stoneHouse1Exit;

	public IslandUnderground(Handler handler, Player player, ChatWindow chatWindow, String path, int worldID) {
		super(handler);
		
		this.worldID = worldID;
		this.player = player;
		this.chatWindow = chatWindow;
		
		width = mapLoader.getMapWidth(path);
		height = mapLoader.getMapHeight(path);
		
		loadWorld(path);
		
		entityManager.addEntity(new Lorraine(handler, 3904, 6080));
		entityManager.addEntity(new Campfire(handler, 4960, 5438));
		entityManager.addEntity(new Campfire(handler, 6016, 5828));
		entityManager.addEntity(new Campfire(handler, 6016, 4900));
		
		house1Exit = new Rectangle(6016, 6192, 32, 32);
		house2Exit = new Rectangle(4960, 6320, 32, 32);
		house3Exit = new Rectangle(3904, 6320, 32, 32);
		beachHouse1Exit = new Rectangle(4960, 5584, 32, 32);
		beachCaveExit = new Rectangle(3728, 5392, 64, 32);
		stoneHouse1Exit = new Rectangle(6016, 5344, 32,32);
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
			chatWindow.tick();
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house1Exit)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(5056);
				handler.getPlayer().setY(5440);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house2Exit)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(4608);
				handler.getPlayer().setY(5400);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house3Exit)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(4384);
				handler.getPlayer().setY(5800);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(beachHouse1Exit)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(5856);
				handler.getPlayer().setY(5824);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(beachCaveExit)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(2688);
				handler.getPlayer().setY(6136);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(stoneHouse1Exit)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(3808);
				handler.getPlayer().setY(5160);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(1).getClass().getSimpleName());
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
						if(getTile(i,x,y) == Tiles.invisible) {
							continue;
						}else {
							getTile(i,x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
						}
					}
				}
			}
			
			// Items
			
			itemManager.render(g);
			
			// Entities
			entityManager.render(g);
			
			/* Uncomment to 
			if(night) {
				renderNight(g);
			}
			*/
			
			renderHPandFPS(g);
			
			// Inventory & Equipment
			inventory.render(g);
			equipment.render(g);
			chatWindow.render(g);
			
			// MiniMap
			miniMap.render(g);
			craftingUI.render(g);
			
//			g.drawRect((int) (exit1.x - handler.getGameCamera().getxOffset()), (int) (exit1.y - handler.getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (exit2.x - handler.getGameCamera().getxOffset()), (int) (exit2.y - handler.getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (exit3.x - handler.getGameCamera().getxOffset()), (int) (exit3.y - handler.getGameCamera().getyOffset()), 32, 32);
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
}
