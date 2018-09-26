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
	private static final long serialVersionUID = -7088128893254827094L;
	public static boolean isOpen = false;
	private int x, y, width, height;
	private Rectangle bounds;
	
	public QuestHelpUI() {
		this.x = 216;
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
			g.drawImage(Assets.shopWindow, x, y, width, height, null);

			if(selectedQuest != null) {
				Text.drawString(g, selectedQuest.getQuestName()+":", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
				
				renderRequirements(g, selectedQuest);
				
				if(selectedQuest.getQuestSteps().size() != 0) {
					Text.drawString(g, "Objective: ", x + (width / 2) + 6, y + 40, true, Color.YELLOW, Assets.font14);
					for(int i = 0; i < Text.splitIntoLine(selectedQuest.getQuestSteps().get(selectedQuest.getStep()).getObjective(), 26).length; i++) {
						Text.drawString(g, Text.splitIntoLine(selectedQuest.getQuestSteps().get(selectedQuest.getStep()).getObjective(), 26)[i], x + (width / 2) + 6, y + 60 + (i * 16), true, Color.YELLOW, Assets.font14);
					}
				}
				else if(selectedQuest.getState() == QuestState.COMPLETED) {
					Text.drawString(g, "Quest complete!", x + (width / 2) + 5, y + 40, true, Color.GREEN, Assets.font14);
				}
			}
			
		}
	}
	
	private void renderRequirements(Graphics g, Quest selectedQuest) {
		if(selectedQuest.getState() == QuestState.NOT_STARTED) {
			Text.drawString(g, "Requirements: ", x + (width / 2) + 6, y + 40, true, Color.YELLOW, Assets.font14);
			if(selectedQuest.getRequirements() != null) {
				Color requirementColor = Color.YELLOW;
				for(int i = 0; i < selectedQuest.getRequirements().length; i++) {
					if(selectedQuest.getRequirements()[i].getSkill() != null) {
						if(Handler.get().getSkill(selectedQuest.getRequirements()[i].getSkill()).getLevel() >= selectedQuest.getRequirements()[i].getLevel()){
							requirementColor = Color.GREEN;
						}else {
							requirementColor = Color.RED;
						}
					}
					else if(selectedQuest.getRequirements()[i].getQuest() != null) {
						if(Handler.get().getQuest(selectedQuest.getRequirements()[i].getQuest()).getState() == QuestState.COMPLETED) {
							requirementColor = Color.GREEN;
						}else {
							requirementColor = Color.RED;
						}
					}
					for(int j = 0; j < Text.splitIntoLine(selectedQuest.getRequirements()[i].getRequirement(), 26).length; j++) {
						if(requirementColor == Color.RED)
							Text.drawString(g, Text.splitIntoLine(selectedQuest.getRequirements()[i].getRequirement(), 26)[j], x + (width / 2) + 6, y + 60 + (i * 16 + j * 16), true, requirementColor, Assets.font14);
						else
							Text.drawStringStrikeThru(g, Text.splitIntoLine(selectedQuest.getRequirements()[i].getRequirement(), 26)[j], x + (width / 2) + 6, y + 60 + (i * 16 + j * 16), true, requirementColor, Assets.font14);
					}
				}
			}else {
				Text.drawString(g, "None", x + (width / 2) + 6, y + 72, true, Color.GREEN, Assets.font14);
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
