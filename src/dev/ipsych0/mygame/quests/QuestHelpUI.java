package dev.ipsych0.mygame.quests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.utils.Text;

public class QuestHelpUI implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean isOpen = false;
	private int x, y, width, height;
	private Handler handler;
	private Rectangle bounds;
	
	public QuestHelpUI(Handler handler) {
		this.handler = handler;
		this.x = 200;
		this.y = 150;
		this.width = 200;
		this.height = 200;
		bounds = new Rectangle(x,y,width,height);
	}
	
	public void tick() {
		if(isOpen) {
			
		}
	}
	
	public void render(Graphics g, Quest selectedQuest) {
		if(isOpen) {
			g.drawImage(Assets.invScreen, x, y, width, height, null);

			if(selectedQuest != null) {
				Text.drawString(g, selectedQuest.getQuestName()+":", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
				
				if(selectedQuest.getState() == QuestState.NOT_STARTED) {
					// Render hier de quest requirements!!!
				}
				if(selectedQuest.getQuestSteps().size() != 0) {
					for(int i = 0; i < Text.splitIntoLine(selectedQuest.getQuestSteps().get(selectedQuest.getStep()).getObjective(), 26).length; i++) {
						Text.drawString(g, Text.splitIntoLine(selectedQuest.getQuestSteps().get(selectedQuest.getStep()).getObjective(), 26)[i], x + (width / 2) + 6, y + 40 + (i * 16), true, Color.YELLOW, Assets.font14);
					}
				}
				else if(selectedQuest.getState() == QuestState.COMPLETED) {
					Text.drawString(g, "Quest complete!", x + (width / 2) + 5, y + 40, true, Color.GREEN, Assets.font14);
				}
			}
			
		}
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
