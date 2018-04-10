package dev.ipsych0.mygame.skills;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class SkillsUI implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int x = 0, y = 180, width = 192, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	public static boolean hasBeenPressed = false;
	private HashMap<SkillsList, Skill> skills;
	private ArrayList<Skill> skillsList;
	private Rectangle crafting, fishing, mining, woodcutting;
	
	public SkillsUI(Handler handler) {
		this.handler = handler;
		skillsList = new ArrayList<Skill>();
		skills = new HashMap<SkillsList, Skill>();
		
		skillsList.add(new CraftingSkill());
		skillsList.add(new WoodcuttingSkill());
		skillsList.add(new FishingSkill());
		skillsList.add(new MiningSkill());
		
		
		// Sort the Skills
		Collections.sort(skillsList, (o1, o2) -> o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName()));
		
		// Sort the Enums
		List<SkillsList> skillsEnum = Arrays.asList(SkillsList.values());
		Collections.sort(skillsEnum, (o1, o2) -> o1.toString().compareTo(o2.toString()));
		
		// Map the skills to the enums
		for(int i = 0; i < skillsList.size(); i++) {
			skills.put(skillsEnum.get(i), skillsList.get(i));
		}
		
		crafting = new Rectangle(x + 8, y + 48, 174, 32);
		fishing = new Rectangle(x + 8, y + 80, 174, 32);
		mining = new Rectangle(x + 8, y + 112, 174, 32);
		woodcutting = new Rectangle(x + 8, y + 144, 174, 32);
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			if(crafting.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					hasBeenPressed = false;
				}
			}
			else if(fishing.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					hasBeenPressed = false;
				}
			}
			else if(mining.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					hasBeenPressed = false;
				}
			}
			else if(woodcutting.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					hasBeenPressed = false;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			Text.drawString(g, "Skills:", x + width / 2, y + 21, true, Color.YELLOW, Assets.font20);
			
			if(crafting.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[0], crafting.x, crafting.y, crafting.width, crafting.height, null);
			}else {
				g.drawImage(Assets.mainMenuButton[1], crafting.x, crafting.y, crafting.width, crafting.height, null);
			}
			if(fishing.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[0], fishing.x, fishing.y, fishing.width, fishing.height, null);
			}else {
				g.drawImage(Assets.mainMenuButton[1], fishing.x, fishing.y, fishing.width, fishing.height, null);
			}
			if(mining.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[0], mining.x, mining.y, mining.width, mining.height, null);
			}else {
				g.drawImage(Assets.mainMenuButton[1], mining.x, mining.y, mining.width, mining.height, null);
			}
			if(woodcutting.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[0], woodcutting.x, woodcutting.y, woodcutting.width, woodcutting.height, null);
			}else {
				g.drawImage(Assets.mainMenuButton[1], woodcutting.x, woodcutting.y, woodcutting.width, woodcutting.height, null);
			}
			Text.drawString(g, "Crafting lvl: " + getSkill(SkillsList.CRAFTING).getLevel(), x + width / 2, y + 64, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Fishing lvl: " + getSkill(SkillsList.FISHING).getLevel() , x + width / 2, y + 96, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Mining lvl: " + getSkill(SkillsList.MINING).getLevel(), x + width / 2, y + 128, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Woodcutting lvl: " + getSkill(SkillsList.WOODCUTTING).getLevel(), x + width / 2, y + 160, true, Color.YELLOW, Assets.font14);
			
			if(crafting.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
				Text.drawString(g, String.valueOf(getSkill(SkillsList.CRAFTING).getExperience())+"/"+getSkill(SkillsList.CRAFTING).getNextLevelXp() + " EXP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
			}
			if(fishing.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
				Text.drawString(g, String.valueOf(getSkill(SkillsList.FISHING).getExperience())+"/"+getSkill(SkillsList.FISHING).getNextLevelXp() + " EXP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
			}
			if(mining.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
				Text.drawString(g, String.valueOf(getSkill(SkillsList.MINING).getExperience())+"/"+getSkill(SkillsList.MINING).getNextLevelXp() + " EXP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
			}
			if(woodcutting.contains(mouse)) {
				g.drawImage(Assets.mainMenuButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
				Text.drawString(g, String.valueOf(getSkill(SkillsList.WOODCUTTING).getExperience())+"/"+getSkill(SkillsList.WOODCUTTING).getNextLevelXp() + " EXP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
			}
		}
	}

	public Skill getSkill(SkillsList skill) {
		return skills.get(skill);
	}
}
