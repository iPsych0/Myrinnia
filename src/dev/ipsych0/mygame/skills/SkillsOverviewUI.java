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
	public int x = 192, y = 180, width = 400, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	private Skill selectedSkill;
	public static boolean hasBeenPressed = false;
	private ScrollBar scrollBar;
	private int maxPerScreen = 8;
	
	public SkillsOverviewUI(Handler handler) {
		this.handler = handler;
		
		scrollBar = new ScrollBar(handler, x + 48 + 32, y + 40, 32, 64, 0, maxPerScreen);
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(selectedSkill != null) {
				scrollBar.tick();
				int yPos = 0;
				if(selectedSkill instanceof CraftingSkill) {
					for(int i = scrollBar.getIndex(); i < scrollBar.getScrollMaximum(); i++) {
						
						Rectangle slot = new Rectangle(x + 16, (y + 40) + (yPos * 32), 32, 32);
						
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
					if(handler.getCraftingUI().getCraftingRecipeList().getRecipes().size() > maxPerScreen) {
						for(int i = scrollBar.getIndex(); i < scrollBar.getIndex() + maxPerScreen; i++) {
							
							Rectangle slot = new Rectangle(x + 16, (y + 40) + (yPos * 32), 32, 32);
							
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + 16, (y + 40) + (yPos * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + 16, (y + 40) + (yPos * 32), 32, 32, null);
							}
							
							if(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).isDiscovered()) {
								g.drawImage(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).getResult().getItem().getTexture(), x + 16, (y + 40) + (yPos * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.undiscovered, x + 16, (y + 40) + (yPos * 32), 32, 32, null);
							}
							Text.drawString(g, String.valueOf(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).getRequiredLevel()), x + 16 + 26, (y + 40) + (yPos * 32) + 26, true, Color.YELLOW, Assets.font14);
							
							scrollBar.render(g);
							
							yPos++;
						}
					}else {
						for(int i = 0; i < handler.getCraftingUI().getCraftingRecipeList().getRecipes().size(); i++) {
							
							Rectangle slot = new Rectangle(x + 16, (y + 40) + (i * 32), 32, 32);
							
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + 16, (y + 40) + (i * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + 16, (y + 40) + (i * 32), 32, 32, null);
							}
							
							if(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).isDiscovered()) {
								g.drawImage(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).getResult().getItem().getTexture(), x + 16, (y + 40) + (i * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.undiscovered, x + 16, (y + 40) + (i * 32), 32, 32, null);
							}
							Text.drawString(g, String.valueOf(handler.getCraftingUI().getCraftingRecipeList().getRecipes().get(i).getRequiredLevel()), x + 16 + 26, (y + 40) + (i * 32) + 26, true, Color.YELLOW, Assets.font14);
							
							scrollBar.render(g);
						}
					}
				}else {
					if(selectedSkill.getResources().size() > maxPerScreen) {
						for(int i = scrollBar.getIndex(); i < scrollBar.getIndex() + maxPerScreen; i++) {
							Rectangle slot = new Rectangle(x + 16, (y + 40) + (yPos * 32), 32, 32);
							
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + 16, (y + 40) + (yPos * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + 16, (y + 40) + (yPos * 32), 32, 32, null);
							}
							
							g.drawImage(selectedSkill.getResources().get(i).getItem().getTexture(), x + 16, (y + 40) + (yPos * 32), 32, 32, null);
							Text.drawString(g, String.valueOf(selectedSkill.getResources().get(i).getLevelRequirement()), x + 16 + 26, (y + 40) + (yPos * 32) + 26, true, Color.YELLOW, Assets.font14);
							
							scrollBar.render(g);
							
							yPos++;
						}
					}else {
						for(int i = 0; i < selectedSkill.getResources().size(); i++) {
							Rectangle slot = new Rectangle(x + 16, (y + 40) + (i * 32), 32, 32);
							
							if(slot.contains(mouse)) {
								g.drawImage(Assets.mainMenuButton[0], x + 16, (y + 40) + (i * 32), 32, 32, null);
							}else {
								g.drawImage(Assets.mainMenuButton[1], x + 16, (y + 40) + (i * 32), 32, 32, null);
							}
							
							g.drawImage(selectedSkill.getResources().get(i).getItem().getTexture(), x + 16, (y + 40) + (i * 32), 32, 32, null);
							Text.drawString(g, String.valueOf(selectedSkill.getResources().get(i).getLevelRequirement()), x + 16 + 26, (y + 40) + (i * 32) + 26, true, Color.YELLOW, Assets.font14);
							
							scrollBar.render(g);
							
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

}
