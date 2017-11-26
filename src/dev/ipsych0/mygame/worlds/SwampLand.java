package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.tiles.Tiles;

public class SwampLand extends World{
	
	private Rectangle testLandTile;

	public SwampLand(Handler handler, Player player, ChatWindow chatWindow, InventoryWindow inventory, EquipmentWindow equipment, String path, int worldID) {
		super(handler);
		
		this.worldID = worldID;
		this.player = player;
		this.chatWindow = chatWindow;
		this.inventory = inventory;
		this.equipment = equipment;
		
		width = mapLoader.getMapWidth(path);
		height = mapLoader.getMapHeight(path);
		
		loadWorld(path);
		
		entityManager.addEntity(new Lorraine(handler, 732, 640));
		
		entityManager.addEntity(new Tree(handler, 160, 128));
		entityManager.addEntity(new Tree(handler, 128, 128));
		entityManager.addEntity(new Tree(handler, 96, 192));
		entityManager.addEntity(new Tree(handler, 96, 160));
		
		entityManager.addEntity(new Rock(handler, 448, 576));
		
		entityManager.addEntity(new Scorpion(handler, 160, 400));
		entityManager.addEntity(new Scorpion(handler, 128, 800));
		entityManager.addEntity(new Scorpion(handler, 128, 888));
		entityManager.addEntity(new Scorpion(handler, 128, 944));
		entityManager.addEntity(new Scorpion(handler, 190, 944));
		entityManager.addEntity(new Scorpion(handler, 190, 888));
		entityManager.addEntity(new Scorpion(handler, 190, 800));
		
		entityManager.addEntity(new TeleportShrine2(handler, 200, 200));
		entityManager.addEntity(new TeleportShrine1(handler, 200, 168));
		
		entityManager.addEntity(new Whirlpool(handler, 672, 432));
		
		testLandTile = new Rectangle(1568, 1300, 32, 200); 

	}
	
	@Override
	public void tick(){
		if(handler.getWorld() == this){
			inventory.tick();
			equipment.tick();
			itemManager.tick();
			entityManager.tick();
			sparkles.tick();
			miniMap.tick();
			craftingUI.tick();
			chatWindow.tick();
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(testLandTile)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(1));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(60);
				handler.getPlayer().setY(164);
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
			
			// Entities & chat
			entityManager.render(g);
			chatWindow.render(g);
			entityManager.postRender(g);
			
			/* Uncomment to 
			if(night) {
				renderNight(g);
			}
			*/
			
			renderHPandFPS(g);
			
			// Inventory & Equipment
			inventory.render(g);
			equipment.render(g);
			
			// MiniMap
			miniMap.render(g);
			craftingUI.render(g);
			
			g.drawRect((int) (testLandTile.x - handler.getGameCamera().getxOffset()), (int) (testLandTile.y - handler.getGameCamera().getyOffset()), 32, 168);
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
}
