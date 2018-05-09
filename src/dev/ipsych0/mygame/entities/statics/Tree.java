package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemType;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Tree extends StaticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private boolean isWoodcutting = false;
	private int woodcuttingTimer = 0;
	private int minAttempts = 3, maxAttempts = 6;
	private int random = 0;
	private int attempts = 0;

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
	}

	@Override
	public void tick() {
		if(isWoodcutting) {
			if(Player.isMoving || handler.getMouseManager().isLeftPressed()) {
				woodcuttingTimer = 0;
				speakingTurn = 1;
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
			
			woodcuttingTimer++;
			
			if(woodcuttingTimer >= 180) {
				System.out.println(random + " and " + attempts);
				int roll = handler.getRandomNumber(1, 100);
	        	if(roll < 70) {
	        		handler.getInventory().getItemSlots().get(handler.getInventory().findFreeSlot(Item.regularLogs)).addItem(Item.regularLogs,
	        				handler.getRandomNumber(1, 3));
	        		handler.sendMsg("You succesfully chopped some logs.");
	        		handler.getSkillsUI().getSkill(SkillsList.WOODCUTTING).addExperience(20);
	        		attempts++;
	        	}else {
	        		handler.sendMsg("Your hatchet bounced off the tree...");
	        		attempts++;
	        	}
	        	speakingTurn = 1;
	        	woodcuttingTimer = 0;
	        	
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
			speakingTurn++;
			return;
		}
		if(this.speakingTurn == 1) {
			if(handler.playerHasSkillLevel(SkillsList.WOODCUTTING, Item.regularLogs)) {
				if(handler.playerHasItemType(ItemType.AXE)) {
					handler.sendMsg("Chop chop...");
					speakingTurn = 2;
					isWoodcutting = true;
				}else {
					handler.sendMsg("You need an axe to chop this tree.");
				}
			}else {
				handler.sendMsg("You need a woodcutting level of " + handler.getSkillResource(SkillsList.WOODCUTTING, Item.regularLogs).getLevelRequirement() + " to chop this tree.");
			}
		}
		if(this.speakingTurn == 2) {
			return;
		}
	}

	@Override
	public void postRender(Graphics g) {
		if(isWoodcutting) {
			g.drawImage(Assets.woodcuttingIcon, (int) (handler.getPlayer().getX() - handler.getGameCamera().getxOffset()), (int) (handler.getPlayer().getY() - handler.getGameCamera().getyOffset() - 32 ), 32, 32, null);
		}
		
	}
	
}
