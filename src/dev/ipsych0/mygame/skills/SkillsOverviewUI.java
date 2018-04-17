package dev.ipsych0.mygame.skills;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.ScrollBar;
import dev.ipsych0.mygame.utils.Text;

public class SkillsOverviewUI implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x = 192, y = 180, width = 256, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	private Skill selectedSkill;
	private SkillCategory selectedCategory;
	public static boolean hasBeenPressed = false;
	private ScrollBar scrollBar;
	private int maxPerScreen = 8;
	
	public SkillsOverviewUI(Handler handler) {
		this.handler = handler;
		
		scrollBar = new ScrollBar(handler, x + width - 40, y + 40, 32, 256, 0, maxPerScreen);
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(selectedSkill != null) {
				scrollBar.tick();
				int yPos = 0;
				if(selectedSkill == handler.getSkillsUI().getSkill(SkillsList.CRAFTING)) {
					for(int i = scrollBar.getIndex(); i < scrollBar.getScrollMaximum(); i++) {
						
						Rectangle slot = new Rectangle(x + width - 80, (y + 40) + (yPos * 32), 32, 32);
						
						if(slot.contains(mouse) && handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).isDiscovered() && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
							handler.sendMsg(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).toString());
							hasBeenPressed = false;
						}
						else if(slot.contains(mouse) && !handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).isDiscovered() && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
							handler.sendMsg("Explore the world or do quests to unlock recipes!");
							hasBeenPressed = false;
						}
						yPos++;
					}
				}else {
					for(int i = scrollBar.getIndex(); i < scrollBar.getScrollMaximum(); i++) {
						
						Rectangle slot = new Rectangle(x + width - 80, (y + 40) + (yPos * 32), 32, 32);
						
						if(slot.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
							handler.sendMsg("You can find this resource in: [Zones]");
							hasBeenPressed = false;
						}
						
						yPos++;
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
				int yPos = 0;
				
				if(selectedSkill instanceof CraftingSkill) {
					g.drawImage(Assets.mainMenuButton[1], x + 16, y + 40, 80, 24, null);
					Text.drawString(g, "Category 1", x + 56, y + 40 + 12, true, Color.YELLOW, Assets.font14);
					g.drawImage(Assets.mainMenuButton[1], x + 16, y + 64, 80, 24, null);
					Text.drawString(g, "Category 2", x + 56, y + 64 + 12, true, Color.YELLOW, Assets.font14);
					g.drawImage(Assets.mainMenuButton[1], x + 16, y + 88, 80, 24, null);
					Text.drawString(g, selectedCategory.getName(), x + 56, y + 88 + 12, true, Color.YELLOW, Assets.font14);
					if(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).size() > maxPerScreen) {
						for(int i = scrollBar.getIndex(); i < scrollBar.getIndex() + maxPerScreen; i++) {
							
							Rectangle slot = new Rectangle(x + width - 80, (y + 40) + (yPos * 32), 32, 32);
							
							g.drawImage(Assets.mainMenuButton[1], x + width - 111, (y + 40) + (yPos * 32), 32, 32, null);
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + width - 80, (y + 40) + (yPos * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + width - 80, (y + 40) + (yPos * 32), 32, 32, null);
							}
							
							if(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).isDiscovered()) {
								g.drawImage(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).getResult().getItem().getTexture(), x + width - 80, (y + 40) + (yPos * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.undiscovered, x + width - 80, (y + 40) + (yPos * 32), 32, 32, null);
							}
							Text.drawString(g, String.valueOf(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).getRequiredLevel()), x + width - 96, (y + 40) + (yPos * 32) + 16, true, Color.YELLOW, Assets.font20);
							
							scrollBar.render(g);
							
							yPos++;
						}
					}else {
						for(int i = 0; i < handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).size(); i++) {
							
							Rectangle slot = new Rectangle(x + width - 80, (y + 40) + (i * 32), 32, 32);
							
							g.drawImage(Assets.mainMenuButton[1], x + width - 111, (y + 40) + (yPos * 32), 32, 32, null);
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + width - 80, (y + 40) + (i * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + width - 80, (y + 40) + (i * 32), 32, 32, null);
							}
							
							if(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).isDiscovered()) {
								g.drawImage(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).getResult().getItem().getTexture(), x + width - 80, (y + 40) + (i * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.undiscovered, x + width - 80, (y + 48) + (i * 32), 32, 32, null);
							}
							Text.drawString(g, String.valueOf(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).getRequiredLevel()), x + width - 96, (y + 40) + (i * 32) + 16, true, Color.YELLOW, Assets.font20);
							
							scrollBar.render(g);
							
							yPos++;
						}
					}
				}else {
					if(selectedSkill.getListByCategory(selectedCategory).size() > maxPerScreen) {
						for(int i = scrollBar.getIndex(); i < scrollBar.getIndex() + maxPerScreen; i++) {
							Rectangle slot = new Rectangle(x + width - 80, (y + 40) + (yPos * 32), 32, 32);
							
							g.drawImage(Assets.mainMenuButton[1], x + width - 111, (y + 40) + (yPos * 32), 32, 32, null);
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + width - 80, (y + 40) + (yPos * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + width - 80, (y + 40) + (yPos * 32), 32, 32, null);
							}
							
							g.drawImage(selectedSkill.getListByCategory(selectedCategory).get(i).getItem().getTexture(), x + width - 80, (y + 40) + (yPos * 32), 32, 32, null);
							Text.drawString(g, String.valueOf(selectedSkill.getListByCategory(selectedCategory).get(i).getLevelRequirement()), x + width - 96, (y + 40) + (yPos * 32) + 16, true, Color.YELLOW, Assets.font20);
							
							scrollBar.render(g);
							
							yPos++;
						}
					}else {
						for(int i = 0; i < selectedSkill.getListByCategory(selectedCategory).size(); i++) {
							Rectangle slot = new Rectangle(x + width - 80, (y + 40) + (i * 32), 32, 32);
							
							g.drawImage(Assets.mainMenuButton[1], x + width - 111, (y + 40) + (yPos * 32), 32, 32, null);
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + width - 80, (y + 40) + (i * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + width - 80, (y + 40) + (i * 32), 32, 32, null);
							}
							
							g.drawImage(selectedSkill.getListByCategory(selectedCategory).get(i).getItem().getTexture(), x + width - 80, (y + 40) + (i * 32), 32, 32, null);
							Text.drawString(g, String.valueOf(selectedSkill.getListByCategory(selectedCategory).get(i).getLevelRequirement()), x + width - 96, (y + 40) + (i * 32) + 16, true, Color.YELLOW, Assets.font20);
							
							scrollBar.render(g);
							yPos++;
						}
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

	public ScrollBar getScrollBar() {
		return scrollBar;
	}

	public void setScrollBar(ScrollBar scrollBar) {
		this.scrollBar = scrollBar;
	}

	public SkillCategory getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(SkillCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

}
