package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.statics.DirtHole;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.WaterToBridgePart;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.tiles.Tiles;

public class Island extends World {
	
	private Rectangle testLandTile;
	private Player player;
	private Rectangle house1;
	private Rectangle house2;
	private Rectangle house3;
	private Rectangle beachHouse;
	private Rectangle beachCave;
	private Rectangle stoneHouse1;

	public Island(Handler handler, Player player, String path, int worldID) {
		super(handler);
		
		this.worldID = worldID;
		this.player = player;
		
		mapLoader = new MapLoader();
		
		width = mapLoader.getMapWidth(path);
		height = mapLoader.getMapHeight(path);
		
		loadWorld(path);
		
		entityManager.addEntity(new Tree(handler, 5248, 5536));
		
		entityManager.addEntity(new Rock(handler, 5280, 5536));
		
		entityManager.addEntity(new Scorpion(handler, 4992, 6048));
		entityManager.addEntity(new Scorpion(handler, 5088, 6112));
		
		entityManager.addEntity(new TeleportShrine2(handler, 5056, 5532));
		entityManager.addEntity(new TeleportShrine1(handler, 5056, 5500));
		
		entityManager.addEntity(new Whirlpool(handler, 5856, 6096));
		
		entityManager.addEntity(new WaterToBridgePart(handler, 3584, 4320));
		entityManager.addEntity(new WaterToBridgePart(handler, 3584, 4352));
		entityManager.addEntity(new WaterToBridgePart(handler, 3584, 4384));
		
		entityManager.addEntity(new DirtHole(handler, 3360, 3136));
		
		testLandTile = new Rectangle(400, 70, 32, 350); 
		house1 = new Rectangle(5056, 5424, 32, 32);
		house2 = new Rectangle(4608, 5360, 32, 32);
		house3 = new Rectangle(4384, 5776, 32, 32);
		beachHouse = new Rectangle(5856, 5808, 32, 32);
		beachCave = new Rectangle(2688, 6120, 32, 32);
		stoneHouse1 = new Rectangle(3808, 5112, 32,32);
		
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
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house1)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(6016);
				handler.getPlayer().setY(6140);
				handler.getPlayer().getChatWindow().sendMessage("X = " + getEntityManager().getPlayer().getX() + " and Y = " + getEntityManager().getPlayer().getY());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house2)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(4960);
				handler.getPlayer().setY(6272);
				handler.getPlayer().getChatWindow().sendMessage("X = " + getEntityManager().getPlayer().getX() + " and Y = " + getEntityManager().getPlayer().getY());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house3)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(3904);
				handler.getPlayer().setY(6272);
				handler.getPlayer().getChatWindow().sendMessage("X = " + getEntityManager().getPlayer().getX() + " and Y = " + getEntityManager().getPlayer().getY());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(beachHouse)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(4960);
				handler.getPlayer().setY(5552);
				handler.getPlayer().getChatWindow().sendMessage("X = " + getEntityManager().getPlayer().getX() + " and Y = " + getEntityManager().getPlayer().getY());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(beachCave)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(3744);
				handler.getPlayer().setY(5360);
				handler.getPlayer().getChatWindow().sendMessage("X = " + getEntityManager().getPlayer().getX() + " and Y = " + getEntityManager().getPlayer().getY());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(stoneHouse1)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(6016);
				handler.getPlayer().setY(5312);
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
			
			inventory.render(g);
			equipment.render(g);
			
			// MiniMap
			miniMap.render(g);
			craftingUI.render(g);
			
			g.drawRect((int) (testLandTile.x - handler.getGameCamera().getxOffset()), (int) (testLandTile.y - handler.getGameCamera().getyOffset()), 32, 350);
			
//			g.drawRect((int) (house1.x - handler.getGameCamera().getxOffset()), (int) (house1.y - handler.getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (house2.x - handler.getGameCamera().getxOffset()), (int) (house2.y - handler.getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (house3.x - handler.getGameCamera().getxOffset()), (int) (house3.y - handler.getGameCamera().getyOffset()), 32, 32);
			
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
}
