package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import java.util.HashMap;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.quests.Quest;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.quests.QuestList;
import dev.ipsych0.mygame.quests.QuestManager;
import dev.ipsych0.mygame.quests.QuestStep;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Campfire extends StaticEntity {

	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private Animation campfire;
	private int speakingTurn;
	private String[] firstDialogue = {"Feel the fire.", "Leave it alone."};
	private String[] secondDialogue = {"That was hot... Wait, I see something... Okay it was nothing, never mind. Wow that was a long string. I should probably split this string up into multiple lines, because this won't work."};
	private String[] thirdDialogue = {"Press this button to continue.", "Press this button to do nothing."};

	public Campfire(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
		speakingTurn = 0;
		campfire = new Animation(125, Assets.campfire);
	}

	@Override
	public void tick() {
		campfire.tick();
	}
	
	@Override
	public void die(){
		
		World currentWorld = handler.getWorld();
		
		// Resapwn
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	
		                currentWorld.getEntityManager().addEntity(new Campfire(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        10000 
		);
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
			chatDialogue = new ChatDialogue(handler, 0, 600, firstDialogue);
			speakingTurn++;
			handler.getQuestManager().getQuestMap().get(QuestList.TutorialQuest).nextStep();
			handler.getQuestManager().getQuestMap().get(QuestList.YellowTest).setState(QuestState.IN_PROGRESS);
			break;
		case 1:
			if(chatDialogue == null) {
				speakingTurn = 0;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				chatDialogue = new ChatDialogue(handler, 0, 600, secondDialogue);
				speakingTurn++;
				break;
			}
			else if(chatDialogue.getChosenOption().getOptionID() == 1) {
				chatDialogue = null;
				speakingTurn = 0;
				break;
			}else {
				speakingTurn = 1;
				break;
			}
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 0;
				break;
			}
			chatDialogue = new ChatDialogue(handler, 0, 600, thirdDialogue);
			speakingTurn++;
			break;
		case 3:
			if(chatDialogue == null) {
				speakingTurn = 0;
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				if(!handler.invIsFull(Item.woodItem)) {
					handler.giveItem(Item.woodItem, 1);
					handler.sendMsg("You found 1 log.");
					chatDialogue = null;
					speakingTurn = 0;
				}else {
					chatDialogue = null;
					speakingTurn = 0;
					handler.sendMsg("Your inventory is full.");
				}
				break;
			}
			else if(chatDialogue.getChosenOption().getOptionID() == 1) {
				speakingTurn = 3;
				break;
			}
		}
//		if(this.speakingTurn == 0) {
//			chatDialogue = new ChatDialogue(handler, 0, 600, true);
//			speakingTurn++;
//		}
//		else if(this.speakingTurn == 1) {
//			if(chatDialogue == null) {
//				speakingTurn = 0;
//				return;
//			}
//			
//			if(chatDialogue.getChosenOption().getOptionID() == 1) {
//				chatDialogue = new ChatDialogue(handler, 0, 600, false);
//				speakingTurn++;
//			}else {
//				speakingTurn = 1;
//			}
//		}
//		else if(this.speakingTurn == 2) {
//			chatDialogue = null;
//			speakingTurn = 0;
//		}
	}

	@Override
	public void postRender(Graphics g) {
		
	}
	
}