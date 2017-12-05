package dev.ipsych0.mygame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Tree extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private int speakingTurn;
	private boolean isWoodcutting = false;
	private int miningTimer = 0;
	private int minAttempts = 2, maxAttempts = 6;
	private int random = 0;
	private int attempts = 0;

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
		isNpc = true;
		attackable = false;
		speakingTurn = 0;
	}

	@Override
	public void tick() {
		if(isWoodcutting) {
			if(Player.isMoving || handler.getMouseManager().isLeftPressed()) {
				miningTimer = 0;
				speakingTurn = 0;
				isWoodcutting = false;
				return;
			}
			if(random != 0) {
				if(attempts == random) {
					attempts = 0;
					isWoodcutting = false;
					this.active = false;
					this.die();
				}
			}
			
			miningTimer++;
			
			if(miningTimer >= 180) {
				System.out.println(random + " and " + attempts);
				int roll = handler.getRandomNumber(1, 100);
	        	if(roll < 60) {
	        		handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(Item.woodItem)).addItem(Item.woodItem,
	        				handler.getRandomNumber(1, 3));
	        		handler.sendMsg("You succesfully chopped some logs.");
	        		attempts++;
	        	}else {
	        		handler.sendMsg("Your hatchet bounced off the tree...");
	        		attempts++;
	        	}
	        	speakingTurn = 0;
	        	miningTimer = 0;
	        	
	        	if(attempts == minAttempts - 1) {
	        		random = handler.getRandomNumber(minAttempts, maxAttempts);
	        	}
			}
			
		}
	}
	
	@Override
	public void die(){
		
		World currentWorld = handler.getWorld();
		
		// Resapwn
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		                currentWorld.getEntityManager().addEntity(new Tree(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        10000 
		);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
		postRender(g);
	}

	@Override
	public void interact() {
		if(this.speakingTurn == 0) {
			handler.sendMsg("Chop chop...");
			speakingTurn = 1;
			isWoodcutting = true;
		}
		if(this.speakingTurn == 1) {
			return;
		}
	}

	@Override
	public void postRender(Graphics g) {
		if(isWoodcutting) {
			g.drawImage(Assets.fish, (int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), 32, 32, null);
		}
		
	}
	
}
