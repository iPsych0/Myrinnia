package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.tiles.Ambiance;
import dev.ipsych0.mygame.tiles.Tiles;

public class TestLand extends World {

	public TestLand(Handler handler, String path) {
		super(handler);
		
		mapLoader = new MapLoader();
		loadGroundTiles(path);
		loadTerrainTiles(path);
		loadAmbianceTiles(path);
		
		entityManager.addEntity(new Lorraine(handler, 732, 640));
		
		entityManager.addEntity(new Tree(handler, 160, 128));
		entityManager.addEntity(new Tree(handler, 128, 128));
		entityManager.addEntity(new Tree(handler, 96, 192));
		entityManager.addEntity(new Tree(handler, 96, 160));
		
		entityManager.addEntity(new Rock(handler, 448, 576));
		
		entityManager.addEntity(new TeleportShrine2(handler, 200, 200));
		entityManager.addEntity(new TeleportShrine1(handler, 200, 168));
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}

	@Override
	public void tick() {
		if(handler.getWorld() == this){
			itemManager.tick();
			entityManager.tick();
			chatWindow.tick();
			sparkles.tick();
			inventory.tick();
			equipment.tick();
			statScreen.tick();
			miniMap.tick();
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
			
			// NPC ChatWindow
			
			chatWindow.render(g);
			
			// MiniMap
			miniMap.render(g);
			
			inventory.render(g);
			equipment.render(g);
			statScreen.render(g);
		}
	}
}
