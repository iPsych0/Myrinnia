package dev.ipsych0.mygame.entities.statics;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.ScreenShot;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.quests.QuestList;
import dev.ipsych0.mygame.recap.RecapEvent;
import dev.ipsych0.mygame.tiles.Tiles;

public class Campfire extends StaticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private Animation campfire;
	private String[] firstDialogue = {"Feel the fire.", "Leave."};
	private String[] secondDialogue = {"You almost burned your fingers trying to examine the fire. However, a sword is revealed."};
	private String[] thirdDialogue = {"Take the sword.", "Leave it."};

	public Campfire(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
		campfire = new Animation(125, Assets.campfire);
	}

	@Override
	public void tick() {
		campfire.tick();
	}
	
	@Override
	public void die(){

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(campfire.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		
		switch(speakingTurn) {
		
		case 0:
			speakingTurn++;
			break;
		case 1:
			chatDialogue = new ChatDialogue(handler, firstDialogue);
			speakingTurn++;
			break;
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				chatDialogue = new ChatDialogue(handler, secondDialogue);
				speakingTurn++;
				if(!handler.questStarted(QuestList.TheFirstQuest)) {
					handler.getQuest(QuestList.TheFirstQuest).setState(QuestState.IN_PROGRESS);
					handler.addQuestStep(QuestList.TheFirstQuest, "Investigate the fire.");
				}
				break;
			}
			else if(chatDialogue.getChosenOption().getOptionID() == 1) {
				chatDialogue = null;
				speakingTurn = 1;
				break;
			}else {
				speakingTurn = 2;
				break;
			}
		case 3:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			chatDialogue = new ChatDialogue(handler, thirdDialogue);
			speakingTurn++;
			break;
		case 4:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				if(handler.getQuest(QuestList.TheFirstQuest).getState() == QuestState.IN_PROGRESS) {
					if(!handler.invIsFull(Item.testSword)) {
						handler.getQuest(QuestList.TheFirstQuest).setState(QuestState.COMPLETED);
						handler.giveItem(Item.testSword, 1);
						handler.discoverRecipe(Item.purpleSword);
						handler.addRecapEvent("Completed The First Quest!");
						chatDialogue = null;
						speakingTurn = 1;
					}else {
						chatDialogue = null;
						speakingTurn = 1;
						handler.sendMsg("Your inventory is full.");
					}
					break;
				}
				else {
					chatDialogue = null;
					speakingTurn = 1;
				}
			}
			else if(chatDialogue.getChosenOption().getOptionID() == 1) {
				chatDialogue = null;
				speakingTurn = 1;
				break;
			}
		}
	}

	@Override
	public void postRender(Graphics g) {
		
	}

	@Override
	public void respawn() {
		handler.getWorld().getEntityManager().addEntity(new Campfire(handler, xSpawn, ySpawn));
	}
	
}