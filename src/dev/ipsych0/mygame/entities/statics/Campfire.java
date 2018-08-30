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

	public Campfire(float x, float y) {
		super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
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
		g.drawImage(campfire.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
				, width, height, null);
	}

	@Override
	public void interact() {
		
		switch(speakingTurn) {
		
		case 0:
			speakingTurn++;
			break;
		case 1:
			chatDialogue = new ChatDialogue(firstDialogue);
			speakingTurn++;
			break;
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				chatDialogue = new ChatDialogue(secondDialogue);
				speakingTurn++;
				if(!Handler.get().questStarted(QuestList.TheFirstQuest)) {
					Handler.get().getQuest(QuestList.TheFirstQuest).setState(QuestState.IN_PROGRESS);
					Handler.get().addQuestStep(QuestList.TheFirstQuest, "Investigate the fire.");
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
			chatDialogue = new ChatDialogue(thirdDialogue);
			speakingTurn++;
			break;
		case 4:
			if(chatDialogue == null) {
				speakingTurn = 1;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				if(Handler.get().getQuest(QuestList.TheFirstQuest).getState() == QuestState.IN_PROGRESS) {
					if(!Handler.get().invIsFull(Item.testSword)) {
						Handler.get().getQuest(QuestList.TheFirstQuest).setState(QuestState.COMPLETED);
						Handler.get().giveItem(Item.testSword, 1);
						Handler.get().discoverRecipe(Item.purpleSword);
						chatDialogue = null;
						speakingTurn = 1;
					}else {
						chatDialogue = null;
						speakingTurn = 1;
						Handler.get().sendMsg("Your inventory is full.");
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
		Handler.get().getWorld().getEntityManager().addEntity(new Campfire(xSpawn, ySpawn));
	}
	
}