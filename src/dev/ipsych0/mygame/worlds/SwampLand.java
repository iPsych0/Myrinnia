package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.utils.MapLoader;

public class SwampLand extends World{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle testLandTile;

	public SwampLand(String path) {
		super();
		
		this.worldPath = path;

		width = MapLoader.getMapWidth(path);
		height = MapLoader.getMapHeight(path);
		
		loadWorld(path);
				
		entityManager.addEntity(new Lorraine(732, 640));
		
		entityManager.addEntity(new Tree(160, 128));
		entityManager.addEntity(new Tree(128, 128));
		entityManager.addEntity(new Tree(96, 192));
		entityManager.addEntity(new Tree(96, 160));
		
		entityManager.addEntity(new Rock(448, 576));
		
//		entityManager.addEntity(new Scorpion(handler, 160, 400));
//		entityManager.addEntity(new Scorpion(handler, 128, 800));
//		entityManager.addEntity(new Scorpion(handler, 128, 888));
//		entityManager.addEntity(new Scorpion(handler, 128, 944));
//		entityManager.addEntity(new Scorpion(handler, 190, 944));
//		entityManager.addEntity(new Scorpion(handler, 190, 888));
//		entityManager.addEntity(new Scorpion(handler, 190, 800));
		
		entityManager.addEntity(new TeleportShrine(200, 200));
		
		entityManager.addEntity(new Whirlpool(672, 432));
		
		testLandTile = new Rectangle(1568, 1300, 32, 200); 

	}
	
	@Override
	public void tick(){
		if(Handler.get().getWorld() == this){
			super.tick();
			if(standingOnTile(testLandTile)){
				Handler.get().goToWorld(Zone.TestLand, 60, 164);
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(Handler.get().getWorld() == this){
			super.render(g);
			
			g.drawRect((int) (testLandTile.x - Handler.get().getGameCamera().getxOffset()), (int) (testLandTile.y - Handler.get().getGameCamera().getyOffset()), 32, 168);
		}
	}
}
