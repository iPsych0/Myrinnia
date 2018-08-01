package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilityhud.PlayerHUD;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.statics.DirtHole;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.entities.statics.WaterToBridgePart;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.utils.MapLoader;

public class Island extends World {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle house1;
	private Rectangle house2;
	private Rectangle house3;
	private Rectangle beachHouse;
	private Rectangle beachCave;
	private Rectangle stoneHouse1;
	private Rectangle southwestVillageHouse;
	private Rectangle northwestVillageHouse;

	private PlayerHUD playerHUD;
	
	public Island(Handler handler, String path) {
		super(handler);
		
		this.worldPath = path;
		
		width = MapLoader.getMapWidth(path);
		height = MapLoader.getMapHeight(path);
		
		loadWorld(path);
		
		entityManager.addEntity(new Tree(handler, 5216, 5536));
		
		entityManager.addEntity(new Rock(handler, 5280, 5536));
		
//		entityManager.addEntity(new Scorpion(handler, 4960, 5700));
		entityManager.addEntity(new Scorpion(handler, 4640, 5740));
//		entityManager.addEntity(new Scorpion(handler, 4600, 5740));
//		entityManager.addEntity(new Scorpion(handler, 4640, 5780));
//		entityManager.addEntity(new Scorpion(handler, 4600, 5780));
		
		entityManager.addEntity(new TeleportShrine(handler, 5056, 5532));
		
		// Beach house
//		entityManager.addEntity(new Whirlpool(handler, 5856, 6096));
		
		// Southern cliffs
		entityManager.addEntity(new Whirlpool(handler, 1280, 6320));
		entityManager.addEntity(new Whirlpool(handler, 4832, 6320));
		entityManager.addEntity(new Whirlpool(handler, 5264, 6240));
		
		entityManager.addEntity(new WaterToBridgePart(handler, 3584, 4320));
		entityManager.addEntity(new WaterToBridgePart(handler, 3584, 4352));
		entityManager.addEntity(new WaterToBridgePart(handler, 3584, 4384));
		
		entityManager.addEntity(new DirtHole(handler, 3360, 3136));
		
		house1 = new Rectangle(5056, 5424, 32, 32);
		house2 = new Rectangle(4608, 5360, 32, 32);
		house3 = new Rectangle(4384, 5776, 32, 32);
		beachHouse = new Rectangle(5856, 5808, 32, 32);
		beachCave = new Rectangle(2688, 6120, 32, 32);
		stoneHouse1 = new Rectangle(3712, 5440, 32,32);
		southwestVillageHouse = new Rectangle(3776, 5784, 32, 32);
		northwestVillageHouse = new Rectangle(4032, 5216, 32, 32);
				
		// Dit is hoe ik items in de world zelf spawn
		itemManager.addItem(Item.regularLogs.createUnequippableItem(5056, 5596, 5), true);
		
		playerHUD = new PlayerHUD(handler, 0, handler.getHeight() - 32);
		
	}

	@Override
	public void tick() {
		if(handler.getWorld().equals(this)){
			super.tick();
			if(standingOnTile(house1)) {
				handler.goToWorld(Zone.IslandUnderground, 6016, 6140);
			}
//			if(getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(house1)){
//				handler.getPlayer().setX(6016);
//				handler.getPlayer().setY(6140);
//				handler.setWorld(handler.getWorldHandler().getWorlds().get(3));
//				handler.getWorld().setHandler(handler);
//			}
			
			if(standingOnTile(house2)){
				handler.goToWorld(Zone.IslandUnderground, 4960, 6272);
			}
			
			if(standingOnTile(house3)){
				handler.goToWorld(Zone.IslandUnderground, 3904, 6272);
			}
			
			if(standingOnTile(beachHouse)){
				handler.goToWorld(Zone.IslandUnderground, 4960, 5552);
			}
			
			if(standingOnTile(beachCave)){
				handler.goToWorld(Zone.IslandUnderground, 3744, 5360);
			}
			
			if(standingOnTile(stoneHouse1)){
				handler.goToWorld(Zone.IslandUnderground, 6016, 5312);
			}
			
			if(standingOnTile(southwestVillageHouse)){
				handler.goToWorld(Zone.IslandUnderground, 4960, 4848);
			}
			
			if(standingOnTile(northwestVillageHouse)){
				handler.goToWorld(Zone.IslandUnderground, 3040, 6288);
			}
		}
		
		playerHUD.tick();
	}

	@Override
	public void render(Graphics g) {
		if(handler.getWorld() == this){
			super.render(g);
			playerHUD.render(g);
//			g.drawRect((int) (house1.x - handler.getGameCamera().getxOffset()), (int) (house1.y - handler.getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (house2.x - handler.getGameCamera().getxOffset()), (int) (house2.y - handler.getGameCamera().getyOffset()), 32, 32);
//			g.drawRect((int) (house3.x - handler.getGameCamera().getxOffset()), (int) (house3.y - handler.getGameCamera().getyOffset()), 32, 32);
			
		}
	}
}
