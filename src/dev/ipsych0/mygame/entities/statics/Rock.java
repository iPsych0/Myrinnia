package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.tiles.Tiles;

public class Rock extends StaticEntity {
	
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();

	public Rock(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
		isNpc = false;
		setNpc(false);
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die(){
		handler.getWorld().getChatWindow().sendMessage("You mined the rock.");
		handler.getWorld().getItemManager().addItem(Item.oreItem.createNew((int) x + 4, (int) y + 6, handler.getRandomSupplyAmount(1, 3)));
		handler.getWorld().getEntityManager().getPlayer().addAttackExperience(100);
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                handler.getWorld().getEntityManager().addEntity(new Rock(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        5000 
		);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
	}

}