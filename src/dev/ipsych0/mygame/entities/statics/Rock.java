package dev.ipsych0.mygame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemType;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Rock extends StaticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private boolean isMining = false;
	private int miningTimer = 0;
	private int minAttempts = 3, maxAttempts = 6;
	private int random = 0;
	private int attempts = 0;

	public Rock(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
	}

	@Override
	public void tick() {
		if(isMining) {
			if(Player.isMoving || handler.getMouseManager().isLeftPressed()) {
				miningTimer = 0;
				speakingTurn = 1;
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
	        		handler.getInventory().getItemSlots().get(handler.getInventory().findFreeSlot(Item.regularOre)).addItem(Item.regularOre,
	        				handler.getRandomNumber(1, 3));
	        		handler.sendMsg("You succesfully mined some ore!");
	        		handler.getSkillsUI().getSkill(SkillsList.MINING).addExperience(10);
	        		attempts++;
	        	}else {
	        		handler.sendMsg("You missed the swing...");
	        		attempts++;
	        	}
	        	speakingTurn = 1;
	        	miningTimer = 0;
	        	
	        	if(attempts == minAttempts - 1) {
	        		random = handler.getRandomNumber(minAttempts, maxAttempts);
	        	}
			}
			
		}
	}
	
	@Override
	public void die(){

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
			speakingTurn++;
			return;
		}
		if(this.speakingTurn == 1) {
			if(handler.playerHasSkillLevel(SkillsList.MINING, Item.regularOre)) {
				if(handler.playerHasItemType(ItemType.PICKAXE)) {
					handler.sendMsg("Mining...");
					speakingTurn = 2;
					isMining = true;
				}else {
					handler.sendMsg("You need a pickaxe to mine this rock.");
				}
			}else {
				handler.sendMsg("You need a mining level of " + handler.getSkillResource(SkillsList.MINING, Item.regularOre).getLevelRequirement() + " to mine this rock.");
			}
		}
		if(this.speakingTurn == 2) {
			return;
		}
	}

	@Override
	public void postRender(Graphics g) {
		if(isMining) {
			g.setColor(Color.WHITE);
			g.drawImage(Assets.miningIcon, (int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), width, height, null);
		}
		
	}

	@Override
	public void respawn() {
		handler.getWorld().getEntityManager().addEntity(new Rock(handler, xSpawn, ySpawn));		
	}
	
}
