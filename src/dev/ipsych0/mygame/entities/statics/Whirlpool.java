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

public class Whirlpool extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private Animation spinning;
	private int speakingTurn;
	private boolean isFishing = false;
	private int fishingTimer = 0;
	private int minAttempts = 4, maxAttempts = 8;
	private int random = 0;
	private int attempts = 0;

	public Whirlpool(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = 32;
		bounds.height = 32;
		isNpc = true;
		attackable = false;
		speakingTurn = 0;
		spinning = new Animation(125, Assets.whirlpool);
	}

	@Override
	public void tick() {
		spinning.tick();
		if(isFishing) {
			if(Player.isMoving || handler.getMouseManager().isLeftPressed()) {
				fishingTimer = 0;
				speakingTurn = 0;
				isFishing = false;
				return;
			}
			if(random != 0) {
				if(attempts == random) {
					attempts = 0;
					isFishing = false;
					this.active = false;
					this.die();
				}
			}
			
			fishingTimer++;
			
			if(fishingTimer >= 180) {
				System.out.println(random + " and " + attempts);
				int roll = handler.getRandomNumber(1, 100);
	        	if(roll < 60) {
	        		handler.getWorld().getInventory().getItemSlots().get(handler.getWorld().getInventory().findFreeSlot(Item.coinsItem)).addItem(Item.coinsItem,
	        				handler.getRandomNumber(1, 5));
	        		handler.getPlayer().getChatWindow().sendMessage("You caught something!");
	        		attempts++;
	        	}else {
	        		handler.getPlayer().getChatWindow().sendMessage("The fish got away...");
	        		attempts++;
	        	}
	        	speakingTurn = 0;
	        	fishingTimer = 0;
	        	
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
		            	
		                currentWorld.getEntityManager().addEntity(new Whirlpool(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        10000 
		);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(spinning.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
		postRender(g);
	}

	@Override
	public void interact() {
		if(this.speakingTurn == 0) {
			handler.getPlayer().getChatWindow().sendMessage("Fishing...");
			speakingTurn = 1;
			isFishing = true;
		}
		if(this.speakingTurn == 1) {
			return;
		}
	}

	@Override
	public void postRender(Graphics g) {
		if(isFishing) {
			g.setColor(Color.WHITE);
			g.fillRect((int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height);
			g.setColor(Color.BLACK);
			g.drawRect((int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height);
			g.drawImage(Assets.fish, (int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height, null);
		}
		
	}
	
}
