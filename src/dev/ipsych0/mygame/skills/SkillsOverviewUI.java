package dev.ipsych0.mygame.skills;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.ScrollBar;
import dev.ipsych0.mygame.utils.Text;

public class SkillsOverviewUI implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x = 192, y = 180, width = 320, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	private Skill selectedSkill;
	private SkillCategory selectedCategory;
	public static boolean hasBeenPressed = false;
	private ScrollBar scrollBar;
	private int maxPerScreen = 8;
	private ArrayList<CategoryButton> categories;
	
	public SkillsOverviewUI(Handler handler) {
		this.handler = handler;
		
		categories = new ArrayList<CategoryButton>();
		
		scrollBar = new ScrollBar(handler, x + width - 40, y + 40, 32, 256, 0, maxPerScreen);
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(selectedSkill != null) {
				scrollBar.tick();
				int yPos = 0;
				
				for(CategoryButton cb : categories) {
					if(cb.getBounds().contains(mouse)) {
						cb.setHovering(true);
						if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
							hasBeenPressed = false;
							selectedCategory = cb.getCategory();
							if(selectedSkill == handler.getSkillsUI().getSkill(SkillsList.CRAFTING)){
								scrollBar.setIndex(0);
								scrollBar.setListSize(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).size());
								scrollBar.setScrollMaximum(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).size());
							}else {
								scrollBar.setIndex(0);
								scrollBar.setListSize(selectedSkill.getListByCategory(selectedCategory).size());
								scrollBar.setScrollMaximum(selectedSkill.getListByCategory(selectedCategory).size());
							}
						}
					}else {
						cb.setHovering(false);
					}
					
					cb.tick();
				}
				
				if(selectedSkill == handler.getSkillsUI().getSkill(SkillsList.CRAFTING)) {
					for(int i = scrollBar.getIndex(); i < scrollBar.getScrollMaximum(); i++) {
						
						Rectangle slot = new Rectangle(x + width - 80, (y + 40) + (yPos * 32), 32, 32);
						
						if(slot.contains(mouse) && handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).isDiscovered() && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
							handler.sendMsg(handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).toString());
							hasBeenPressed = false;
						}
						else if(slot.contains(mouse) && !handler.getCraftingUI().getCraftingRecipeList().getListByCategory(selectedCategory).get(i).isDiscovered() && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
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
				
				for(CategoryButton cb : categories) {
					cb.render(g);
				}
				
				if(selectedSkill == handler.getSkillsUI().getSkill(SkillsList.CRAFTING)) {
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

	public ArrayList<CategoryButton> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<CategoryButton> categories) {
		this.categories = categories;
	}

}
