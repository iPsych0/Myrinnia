package dev.ipsych0.mygame.skills;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class SkillsOverviewUI implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x = 192, y = 180, width = 400, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	private Skill selectedSkill;
	public static boolean hasBeenPressed = false;
	
	
	public SkillsOverviewUI(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(selectedSkill != null) {
				if(selectedSkill instanceof CraftingSkill) {
					for(int i = 0; i < handler.getCraftingUI().getCraftingRecipeList().getRecipes().size(); i++) {
						
						Rectangle slot = new Rectangle(x + 16, (y + 24) + (i * 32), 32, 32);
						
						if(slot.contains(mouse) && handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).isDiscovered() && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
							handler.sendMsg(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).toString());
							hasBeenPressed = false;
						}
						else if(slot.contains(mouse) && !handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).isDiscovered() && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
							handler.sendMsg("Explore the world or do quests to unlock recipes!");
							hasBeenPressed = false;
						}
					}
				}else {
					for(int i = 0; i < selectedSkill.getResources().size(); i++) {
						
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);

			if(selectedSkill != null) {
				Text.drawString(g, selectedSkill.toString(), x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);
				if(selectedSkill instanceof CraftingSkill) {
					for(int i = 0; i < handler.getCraftingUI().getCraftingRecipeList().getRecipes().size(); i++) {
						
						Rectangle slot = new Rectangle(x + 16, (y + 24) + (i * 32), 32, 32);
						
						if(slot.contains(mouse)) {
							g.drawImage(Assets.mainMenuButton[0], x + 16, (y + 24) + (i * 32), 32, 32, null);
						}else {
							g.drawImage(Assets.mainMenuButton[1], x + 16, (y + 24) + (i * 32), 32, 32, null);
						}
						
						if(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).isDiscovered()) {
							g.drawImage(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).getResult().getItem().getTexture(), x + 16, (y + 24) + (i * 32), 32, 32, null);
						}else {
							g.drawImage(Assets.undiscovered, x + 16, (y + 24) + (i * 32), 32, 32, null);
						}
						Text.drawString(g, String.valueOf(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).getRequiredLevel()), x + 16 + 26, (y + 24) + (i * 32) + 26, true, Color.YELLOW, Assets.font14);
					}
				}else {
					for(int i = 0; i < selectedSkill.getResources().size(); i++) {
						Rectangle slot = new Rectangle(x + 16, (y + 24) + (i * 32), 32, 32);
						
						if(slot.contains(mouse)) {
							g.drawImage(Assets.mainMenuButton[0], x + 16, (y + 24) + (i * 32), 32, 32, null);
						}else {
							g.drawImage(Assets.mainMenuButton[1], x + 16, (y + 24) + (i * 32), 32, 32, null);
						}
						
						g.drawImage(selectedSkill.getResources().get(i).getItem().getTexture(), x + 16, (y + 24) + (i * 32), 32, 32, null);
						Text.drawString(g, String.valueOf(selectedSkill.getResources().get(i).getLevelRequirement()), x + 16 + 26, (y + 24) + (i * 32) + 26, true, Color.YELLOW, Assets.font14);
					}
				}
			}
		}
	}

	public Skill getSelectedSkill() {
		return selectedSkill;
	}

	public void setSelectedSkill(Skill selectedSkill) {
		this.selectedSkill = selectedSkill;
	}

}
