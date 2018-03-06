package dev.ipsych0.mygame.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.npcs.ChatDialogue;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.quests.QuestList;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class WaterToBridgePart extends StaticEntity {
	
	private int xSpawn = (int) getX();
	private int ySpawn = (int) getY();
	private boolean isFixed = false;
	private int speakingTurn = 0;
	private String[] firstDialogue = {"This bridge part looks like it can be fixed with some logs. I think 5 logs should do."};
	private String[] secondDialogue = {"Fix the bridge. (Use 5 logs)","Leave the bridge."};
	
	public WaterToBridgePart(Handler handler, float x, float y) {
		super(handler, x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);
		
		isNpc = true;
		attackable = false;
	}

	@Override
	public void tick() {
		if(isFixed) {
			die();
		}
	}
	
	@Override
	public void die(){
		this.active = false;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.waterMiddleMiddle, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
			, width, height, null);
	}

	@Override
	public void interact() {
		
		switch(speakingTurn) {
		
		case 0:
			if(handler.getQuest(QuestList.TheFirstQuest).getState() == QuestState.COMPLETED) {
				chatDialogue = new ChatDialogue(handler, 0, 600, firstDialogue);
				if(handler.getQuest(QuestList.TheSecondQuest).getState() != QuestState.IN_PROGRESS) {
					handler.getQuest(QuestList.TheSecondQuest).setState(QuestState.IN_PROGRESS);
					handler.addQuestStep(QuestList.TheSecondQuest, "Fix the bridge [0/3]");
				}
				speakingTurn++;
				break;
			}else {
				handler.sendMsg("Please complete The First Quest to proceed.");
				speakingTurn = 0;
				break;
			}
			
		case 1:
			if(chatDialogue == null) {
				speakingTurn = 0;
				interact();
				break;
			}
			if(handler.playerHasItem(Item.woodItem, 5)) {
				chatDialogue = new ChatDialogue(handler, 0, 600, secondDialogue);
				speakingTurn++;
				break;
			}else {
				chatDialogue = null;
				speakingTurn = 0;
				break;
			}
		case 2:
			if(chatDialogue == null) {
				speakingTurn = 0;
				interact();
				break;
			}
			
			if(chatDialogue.getChosenOption().getOptionID() == 0) {
				chatDialogue = null;
				if(handler.playerHasItem(Item.woodItem, 5)) {
					handler.removeItem(Item.woodItem, 5);
					isFixed = true;
					handler.sendMsg("You fixed the bridge!");
					if(handler.getQuest(QuestList.TheSecondQuest).getState() == QuestState.IN_PROGRESS) {
						if(handler.getQuest(QuestList.TheSecondQuest).getStep() == 2) {
							handler.getQuest(QuestList.TheSecondQuest).setState(QuestState.COMPLETED);
						}else {
							handler.addQuestStep(QuestList.TheSecondQuest, "Fix the bridge ["+ (handler.getQuest(QuestList.TheSecondQuest).getStep()+1) +"/3]");
							handler.getQuest(QuestList.TheSecondQuest).nextStep();
						}
					}
					break;
				}else {
					speakingTurn = 0;
					break;
				}
			}
			else if(chatDialogue.getChosenOption().getOptionID() == 1) {
				chatDialogue = null;
				speakingTurn = 0;
				break;
			}
		}
		
		
	}

	@Override
	public void postRender(Graphics g) {
		
	}

}