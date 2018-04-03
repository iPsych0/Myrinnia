package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.Whirlpool;

public class TestLand extends World {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle swampLandTile;
	private Rectangle islandTile;

	public TestLand(Handler handler, String path, int worldID) {
		super(handler);
		
		width = mapLoader.getMapWidth(path);
		height = mapLoader.getMapHeight(path);
		
		loadWorld(path);
		
		entityManager.addEntity(new Lorraine(handler, 732, 440));
		
		entityManager.addEntity(new Tree(handler, 360, 128));
		entityManager.addEntity(new Tree(handler, 328, 128));
		entityManager.addEntity(new Tree(handler, 296, 192));
		entityManager.addEntity(new Tree(handler, 296, 160));
		
		entityManager.addEntity(new Rock(handler, 448, 576));
		
		entityManager.addEntity(new TeleportShrine(handler, 200, 200));
		
		entityManager.addEntity(new Whirlpool(handler, 112, 928));
		
		swampLandTile = new Rectangle(0, 70, 32, 350); 
		islandTile = new Rectangle(1568, 70, 32, 350);
	}

	@Override
	public void tick() {
		if(handler.getWorld() == this){
			super.tick();
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(swampLandTile)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(1490);
				handler.getPlayer().setY(1305);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(0).getClass().getSimpleName());
			}
			
			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(islandTile)){
				handler.setWorld(handler.getWorldHandler().getWorlds().get(2));
				handler.getWorld().setHandler(handler);
				handler.getPlayer().setX(800);
				handler.getPlayer().setY(750);
				System.out.println("Went to world: " + handler.getWorldHandler().getWorlds().get(2).getClass().getSimpleName());
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(handler.getWorld() == this){
			super.render(g);
			
			g.drawRect((int) (swampLandTile.x - handler.getGameCamera().getxOffset()), (int) (swampLandTile.y - handler.getGameCamera().getyOffset()), 32, 350);
			g.drawRect((int) (islandTile.x - handler.getGameCamera().getxOffset()), (int) (islandTile.y - handler.getGameCamera().getyOffset()), 32, 350);
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public void setWorldID(int worldID) {
		this.worldID = worldID;
	}
}
