package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.tiles.Ambiance;
import dev.ipsych0.mygame.tiles.Tiles;

public class Island extends World {
	
	private Rectangle testLandTile;
	private Player player;

	public Island(Handler handler, Player player, String path, int worldID) {
		super(handler);
		
		this.worldID = worldID;
		this.player = player;
		
		mapLoader = new MapLoader();
		
		width = mapLoader.getMapWidth(path);
		height = mapLoader.getMapHeight(path);
		
		loadGroundTiles(path);
		loadTerrainTiles(path);
		loadAmbianceTiles(path);
		
		entityManager.addEntity(new Lorraine(handler, 732, 440));
		
		entityManager.addEntity(new Tree(handler, 360, 128));
		entityManager.addEntity(new Tree(handler, 328, 128));
		entityManager.addEntity(new Tree(handler, 296, 192));
		entityManager.addEntity(new Tree(handler, 296, 160));
		
		entityManager.addEntity(new Rock(handler, 448, 576));
		
		entityManager.addEntity(new TeleportShrine2(handler, 200, 200));
		entityManager.addEntity(new TeleportShrine1(handler, 200, 168));
		
		entityManager.addEntity(new Whirlpool(handler, 112, 928));
		
		testLandTile = new Rectangle(400, 70, 32, 350); 
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
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(testLandTile)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(1));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(1520);
				handler.getPlayer().setY(164);
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
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					getTile(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
				}
			}
			
			// Render the terrain tiles
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					getTerrain(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
				}
			}
			
			// Render the ambiance tiles
			for(int y = yStart; y < yEnd; y++){
				for(int x = xStart; x < xEnd; x++){
					if(getAmbiance(x, y) == Ambiance.sparkleTile){
						g.drawImage(sparkles.getCurrentFrame(), (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
								(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()), null);
					}
					getAmbiance(x,y).render(g, (int) (x * Tiles.TILEWIDTH - handler.getGameCamera().getxOffset()), 
							(int) (y * Tiles.TILEHEIGHT - handler.getGameCamera().getyOffset()));
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
			
			g.drawRect((int) (testLandTile.x - handler.getGameCamera().getxOffset()), (int) (testLandTile.y - handler.getGameCamera().getyOffset()), 32, 350);
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
}