package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Campfire;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.quests.QuestManager;
import dev.ipsych0.mygame.tiles.Tiles;

public class IslandUnderground extends World {
	
	private Rectangle house1Exit;
	private Rectangle house2Exit;
	private Rectangle house3Exit;
	private Rectangle beachHouse1Exit;
	private Rectangle beachCaveExit;
	private Rectangle stoneHouse1Exit;

	public IslandUnderground(Handler handler, String path, int worldID) {
		super(handler);
		
		this.worldID = worldID;
		
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
			super.tick();
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house1Exit)){
				handler.getPlayer().setX(5056);
				handler.getPlayer().setY(5440);
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house2Exit)){
				handler.getPlayer().setX(4608);
				handler.getPlayer().setY(5400);
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house3Exit)){
				handler.getPlayer().setX(4384);
				handler.getPlayer().setY(5800);
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(beachHouse1Exit)){
				handler.getPlayer().setX(5856);
				handler.getPlayer().setY(5824);
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(beachCaveExit)){
				handler.getPlayer().setX(2688);
				handler.getPlayer().setY(6136);
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(stoneHouse1Exit)){
				handler.getPlayer().setX(3808);
				handler.getPlayer().setY(5160);
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(handler.getWorld() == this){
			super.render(g);
			
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
