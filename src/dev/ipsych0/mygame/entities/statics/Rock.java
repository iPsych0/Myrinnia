package dev.ipsych0.mygame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemType;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.tiles.Tiles;

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

	public Rock(float x, float y) {
		super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
	}

	@Override
	public void tick() {
		if(isMining) {
			if(Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
					!Handler.get().getPlayer().hasLeftClickedUI(new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1))) {
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
				int roll = Handler.get().getRandomNumber(1, 100);
	        	if(roll < 60) {
	        		Handler.get().getInventory().getItemSlots().get(Handler.get().getInventory().findFreeSlot(Item.regularOre)).addItem(Item.regularOre,
	        				Handler.get().getRandomNumber(1, 3));
	        		Handler.get().sendMsg("You succesfully mined some ore!");
	        		Handler.get().getSkillsUI().getSkill(SkillsList.MINING).addExperience(10);
	        		attempts++;
	        	}else {
	        		Handler.get().sendMsg("You missed the swing...");
	        		attempts++;
	        	}
	        	speakingTurn = 1;
	        	miningTimer = 0;
	        	
	        	if(attempts == minAttempts - 1) {
	        		random = Handler.get().getRandomNumber(minAttempts, maxAttempts);
	        	}
			}
			
		}
	}
	
	@Override
	public void die(){

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
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
			if(Handler.get().playerHasSkillLevel(SkillsList.MINING, Item.regularOre)) {
				if(Handler.get().playerHasItemType(ItemType.PICKAXE)) {
					Handler.get().sendMsg("Mining...");
					speakingTurn = 2;
					isMining = true;
				}else {
					Handler.get().sendMsg("You need a pickaxe to mine this rock.");
				}
			}else {
				Handler.get().sendMsg("You need a mining level of " + Handler.get().getSkillResource(SkillsList.MINING, Item.regularOre).getLevelRequirement() + " to mine this rock.");
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
			g.drawImage(Assets.miningIcon, (int) (Handler.get().getPlayer().getX() - Handler.get().getGameCamera().getxOffset()), (int) (Handler.get().getPlayer().getY() - Handler.get().getGameCamera().getyOffset() - 32 ), width, height, null);
		}
		
	}

	@Override
	public void respawn() {
		Handler.get().getWorld().getEntityManager().addEntity(new Rock(xSpawn, ySpawn));		
	}
	
}
