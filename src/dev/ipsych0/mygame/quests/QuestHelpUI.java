package dev.ipsych0.mygame.quests;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.utils.Text;

public class QuestHelpUI {
	
	public static boolean isOpen = false;
	private int x, y, width, height;
	private Handler handler;
	
	public QuestHelpUI(Handler handler) {
		this.handler = handler;
		this.x = 200;
		this.y = 150;
		this.width = 200;
		this.height = 200;
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
				
				if(selectedQuest.getQuestSteps().size() != 0) {
					for(int i = 0; i < Text.splitIntoLine(selectedQuest.getQuestSteps().get(selectedQuest.getStep()).getObjective(), 26).length; i++) {
						Text.drawString(g, Text.splitIntoLine(selectedQuest.getQuestSteps().get(selectedQuest.getStep()).getObjective(), 26)[i], x + (width / 2) + 7, y + 41 + (i * 16), true, Color.BLACK, Assets.font14);
					}
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

}
