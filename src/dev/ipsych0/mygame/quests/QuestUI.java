package dev.ipsych0.mygame.quests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.utils.Text;
import dev.ipsych0.mygame.worlds.Zone;

public class QuestUI implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean isOpen = false;
	private int x, y, width, height;
	private QuestHelpUI questHelpUI;
	private Quest selectedQuest;
	private Zone selectedZone;
	public static boolean hasBeenPressed;
	private Rectangle bounds;
	public static boolean renderingQuests = false;
	public static boolean escapePressed = false;
	
	public QuestUI() {
		this.x = 0;
		this.y = 150;
		this.width = 200;
		this.height = 400;
		bounds = new Rectangle(x,y,width,height);
		
		questHelpUI = new QuestHelpUI();
	}
	
	public void tick() {
		if(isOpen) {
			if(QuestHelpUI.isOpen && Handler.get().getKeyManager().escape && escapePressed) {
				hasBeenPressed = false;
				escapePressed = false;
				QuestHelpUI.isOpen = false;
			}
			else if(!QuestHelpUI.isOpen && renderingQuests && Handler.get().getKeyManager().escape && escapePressed) {
				hasBeenPressed = false;
				escapePressed = false;
				renderingQuests = false;
			}
			else if(!QuestHelpUI.isOpen && Handler.get().getKeyManager().escape && escapePressed) {
				hasBeenPressed = false;
				escapePressed = false;
				renderingQuests = false;
				isOpen = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			Rectangle mouse = Handler.get().getMouse();
			
			if(!renderingQuests) {
				Text.drawString(g, "Zones:", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
				for(int i = 0; i < Handler.get().getQuestManager().getAllQuestLists().size(); i++) {
					Rectangle text = new Rectangle(x + 4, y + 32 + (i * 16), width - 8, 16);
					if(text.contains(mouse)) {
						g.drawImage(Assets.genericButton[0], text.x + 4, text.y, text.width - 8, text.height - 1, null);
						if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
							hasBeenPressed = false;
							for(int j = 0; j < Handler.get().getQuestManager().getAllQuestLists().get(i).size(); j++) {
								if(selectedZone == Handler.get().getQuestManager().getAllQuestLists().get(i).get(j).getZone()) {
									renderingQuests = true;
								}
								else if(selectedZone != Handler.get().getQuestManager().getAllQuestLists().get(i).get(j).getZone()) {
									renderingQuests = true;
								}
								else if(selectedZone == null) {
									renderingQuests = false;
								}
								selectedZone = Handler.get().getQuestManager().getAllQuestLists().get(i).get(j).getZone();
							}
						}
					}else {
						g.drawImage(Assets.genericButton[1], text.x + 4, text.y, text.width - 8, text.height - 1, null);
					}
					Text.drawString(g, Handler.get().getQuestManager().getAllQuestLists().get(i).get(0).getZone().toString(), x + (width / 2) + 1, y + 41 + (i * 16), true, Color.YELLOW, Assets.font14);
					
					Rectangle backButton = new Rectangle(x + (width / 2) / 2, y + height - 24, width / 2, 16);
					
					if(backButton.contains(mouse)) {
						g.drawImage(Assets.genericButton[0], backButton.x, backButton.y, backButton.width, backButton.height, null);
						if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
							renderingQuests = false;
							QuestUI.isOpen = false;
							hasBeenPressed = false;
						}
					}
					else {
						g.drawImage(Assets.genericButton[1], backButton.x, backButton.y, backButton.width, backButton.height, null);
					}

					Text.drawString(g, "Exit", x + (width / 2), y + height - 16, true, Color.YELLOW, Assets.font14);
				}
			}else {
				Text.drawString(g, "Quests:", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
				for(int i = 0; i < Handler.get().getQuestManager().getZoneMap().get(selectedZone).size(); i++) {
					Color color;
					Rectangle text = new Rectangle(x + 4, y + 32 + (i * 16), width - 8, 16);
					if(Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i).getState() == QuestState.NOT_STARTED)
						color = new Color(255,0,0);
					else if(Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i).getState() == QuestState.IN_PROGRESS)
						color = Color.YELLOW;
					else
						color = Color.GREEN;
					
					if(text.contains(mouse)) {
						g.drawImage(Assets.genericButton[0], text.x + 4, text.y, text.width - 8, text.height - 1, null);
						if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
							hasBeenPressed = false;
							if(selectedQuest == Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i)) {
								QuestHelpUI.isOpen = !QuestHelpUI.isOpen;
							}
							else if(selectedQuest != Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i)) {
								QuestHelpUI.isOpen = true;
							}
							else if(selectedQuest == null) {
								QuestHelpUI.isOpen = true;
							}
							selectedQuest = Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i);
						}
					}else {
						g.drawImage(Assets.genericButton[1], text.x + 4, text.y, text.width - 8, text.height - 1, null);
					}
				Text.drawString(g, Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i).getQuestName(), x + (width / 2) + 1, y + 41 + (i * 16), true, color, Assets.font14);
				}
				
				Rectangle backButton = new Rectangle(x + (width / 2) / 2, y + height - 24, width / 2, 16);
				
				if(backButton.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], backButton.x, backButton.y, backButton.width, backButton.height, null);
					if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
						renderingQuests = false;
						QuestHelpUI.isOpen = false;
						hasBeenPressed = false;
					}
				}
				else {
					g.drawImage(Assets.genericButton[1], backButton.x, backButton.y, backButton.width, backButton.height, null);
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
