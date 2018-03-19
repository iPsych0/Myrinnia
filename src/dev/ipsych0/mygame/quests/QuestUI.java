package dev.ipsych0.mygame.quests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.utils.Text;
import dev.ipsych0.mygame.worlds.Zone;

public class QuestUI {
	
	public static boolean isOpen = false;
	private int x, y, width, height;
	private Handler handler;
	private QuestHelpUI questHelpUI;
	private Quest selectedQuest;
	private Zone selectedZone;
	public static boolean hasBeenPressed;
	private Rectangle bounds;
	public static boolean renderingQuests = false;
	
	public QuestUI(Handler handler) {
		this.handler = handler;
		this.x = 0;
		this.y = 150;
		this.width = 200;
		this.height = 400;
		bounds = new Rectangle(x,y,width,height);
		
		questHelpUI = new QuestHelpUI(handler);
	}
	
	public void tick() {
		if(isOpen) {
			
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.invScreen, x, y, width, height, null);
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(!renderingQuests) {
				Text.drawString(g, "Zones:", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
				for(int i = 0; i < handler.getQuestManager().getAllQuestLists().size(); i++) {
					Rectangle text = new Rectangle(x, y + 32 + (i * 16), width, 16);
					if(text.contains(mouse)) {
						g.drawImage(Assets.mainMenuButton[0], text.x + 4, text.y, text.width - 8, text.height - 1, null);
						if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
							hasBeenPressed = false;
							for(int j = 0; j < handler.getQuestManager().getAllQuestLists().get(i).size(); j++) {
								if(selectedZone == handler.getQuestManager().getAllQuestLists().get(i).get(j).getZone()) {
									renderingQuests = true;
								}
								else if(selectedZone != handler.getQuestManager().getAllQuestLists().get(i).get(j).getZone()) {
									renderingQuests = true;
								}
								else if(selectedZone == null) {
									renderingQuests = false;
								}
								selectedZone = handler.getQuestManager().getAllQuestLists().get(i).get(j).getZone();
							}
						}
					}else {
						g.drawImage(Assets.mainMenuButton[1], text.x + 4, text.y, text.width - 8, text.height - 1, null);
					}
					Text.drawString(g, handler.getQuestManager().getAllQuestLists().get(i).get(0).getZone().toString(), x + (width / 2) + 1, y + 41 + (i * 16), true, Color.YELLOW, Assets.font14);
				}
			}else {
				Text.drawString(g, "Quests:", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
				for(int i = 0; i < handler.getQuestManager().getZoneMap().get(selectedZone).size(); i++) {
					Color color;
					Rectangle text = new Rectangle(x, y + 32 + (i * 16), width, 16);
					if(handler.getQuestManager().getZoneMap().get(selectedZone).get(i).getState() == QuestState.NOT_STARTED)
						color = new Color(255,0,0);
					else if(handler.getQuestManager().getZoneMap().get(selectedZone).get(i).getState() == QuestState.IN_PROGRESS)
						color = Color.YELLOW;
					else
						color = Color.GREEN;
					
					if(text.contains(mouse)) {
						g.drawImage(Assets.mainMenuButton[0], text.x + 4, text.y, text.width - 8, text.height - 1, null);
						if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
							hasBeenPressed = false;
							if(selectedQuest == handler.getQuestManager().getZoneMap().get(selectedZone).get(i)) {
								QuestHelpUI.isOpen = !QuestHelpUI.isOpen;
							}
							else if(selectedQuest != handler.getQuestManager().getZoneMap().get(selectedZone).get(i)) {
								QuestHelpUI.isOpen = true;
							}
							else if(selectedQuest == null) {
								QuestHelpUI.isOpen = true;
							}
							selectedQuest = handler.getQuestManager().getZoneMap().get(selectedZone).get(i);
						}
					}else {
						g.drawImage(Assets.mainMenuButton[1], text.x + 4, text.y, text.width - 8, text.height - 1, null);
					}
				Text.drawString(g, handler.getQuestManager().getZoneMap().get(selectedZone).get(i).getQuestName(), x + (width / 2) + 1, y + 41 + (i * 16), true, color, Assets.font14);
				}
				
				Rectangle backButton = new Rectangle(x + (width / 2) / 2, y + height - 24, width / 2, 16);
				
				if(backButton.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], backButton.x, backButton.y, backButton.width, backButton.height, null);
					if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged()) {
						renderingQuests = false;
					}
				}
				else {
					g.drawImage(Assets.mainMenuButton[1], backButton.x, backButton.y, backButton.width, backButton.height, null);
				}

				Text.drawString(g, "Back", x + (width / 2), y + height - 16, true, Color.YELLOW, Assets.font14);
			}
			
			if(QuestHelpUI.isOpen) {
				questHelpUI.render(g, selectedQuest);
			}
		}
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public QuestHelpUI getQuestHelpUI() {
		return questHelpUI;
	}

	public void setQuestHelpUI(QuestHelpUI questHelpUI) {
		this.questHelpUI = questHelpUI;
	}

}
