package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Campfire;
import dev.ipsych0.mygame.utils.MapLoader;

public class IslandUnderground extends World {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle house1Exit;
	private Rectangle house2Exit;
	private Rectangle house3Exit;
	private Rectangle beachHouse1Exit;
	private Rectangle beachCaveExit;
	private Rectangle stoneHouse1Exit;

	public IslandUnderground(Handler handler, String path, int worldID) {
		super(handler);
		
		this.worldPath = path;
		this.worldID = worldID;
		
		width = MapLoader.getMapWidth(path);
		height = MapLoader.getMapHeight(path);
		
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
			if(standingOnTile(house1Exit)){
				handler.goToWorld(0, 5056, 5440);
			}
			
			if(standingOnTile(house2Exit)){
				handler.goToWorld(0, 4608, 5400);
			}
			
			if(standingOnTile(house3Exit)){
				handler.goToWorld(0, 4384, 5800);
			}
			
			if(standingOnTile(beachHouse1Exit)){
				handler.goToWorld(0, 5856, 5824);
			}
			
			if(standingOnTile(beachCaveExit)){
				handler.goToWorld(0, 2688, 6136);
			}
			
			if(standingOnTile(stoneHouse1Exit)){
				handler.goToWorld(0, 3808, 5160);
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
