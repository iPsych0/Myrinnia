package dev.ipsych0.mygame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Rock extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private int speakingTurn;
	private boolean isMining = false;
	private int miningTimer = 0;
	private int minAttempts = 2, maxAttempts = 6;
	private int random = 0;
	private int attempts = 0;

	public Rock(Handler handler, float x, float y) {
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
		if(isMining) {
			if(Player.isMoving || handler.getMouseManager().isLeftPressed()) {
				miningTimer = 0;
				speakingTurn = 0;
				isMining = false;
				return;
			}
			if(random != 0) {
				if(attempts == random) {
					attempts = 0;
					isMining = false;
					this.active = false;
					this.die();
				}
			}
			
			miningTimer++;
			
			if(miningTimer >= 180) {
				System.out.println(random + " and " + attempts);
				int roll = handler.getRandomNumber(1, 100);
	        	if(roll < 60) {
	        		handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(Item.oreItem)).addItem(Item.oreItem,
	        				handler.getRandomNumber(1, 3));
	        		handler.getPlayer().getChatWindow().sendMessage("You succesfully mined some ore!");
	        		attempts++;
	        	}else {
	        		handler.getPlayer().getChatWindow().sendMessage("You missed the swing...");
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
		            	
		                currentWorld.getEntityManager().addEntity(new Rock(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        10000 
		);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
		postRender(g);
	}

	@Override
	public void interact() {
		if(this.speakingTurn == 0) {
			handler.getPlayer().getChatWindow().sendMessage("Mining...");
			speakingTurn = 1;
			isMining = true;
		}
		if(this.speakingTurn == 1) {
			return;
		}
	}

	@Override
	public void postRender(Graphics g) {
		if(isMining) {
			g.setColor(Color.WHITE);
			g.fillRect((int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height);
			g.setColor(Color.BLACK);
			g.drawRect((int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height);
			g.drawImage(Assets.fish, (int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height, null);
		}
		
	}
	
}
