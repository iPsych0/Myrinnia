package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import java.util.Random;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.tiles.Tiles;

public class Whirlpool extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private Animation spinning;
	private int speakingTurn;

	public Whirlpool(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
		isNpc = true;
		attackable = false;
		speakingTurn = 0;
		spinning = new Animation(300, Assets.whirlpool);
	}

	@Override
	public void tick() {
		spinning.tick();
	}
	
	@Override
	public void die(){

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(spinning.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		if(this.speakingTurn == 0) {
			handler.getPlayer().getChatWindow().sendMessage("Fishing...");
			speakingTurn = 1;
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	int roll = handler.getRandomSupplyAmount(1, 100);
			            	if(roll < 25) {
			            		handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(Item.coinsItem)).addItem(Item.coinsItem,
			            				handler.getRandomSupplyAmount(1, 5));
			            		handler.getPlayer().getChatWindow().sendMessage("You caught something!");
			            		speakingTurn = 0;
			            	}else {
			            		handler.getPlayer().getChatWindow().sendMessage("You didn't catch anything...");
			            		speakingTurn = 0;
			            	}
			            }
			        }, 
			        3000 
			);
		}
		if(this.speakingTurn == 1) {
			
		}
	}
	
}
