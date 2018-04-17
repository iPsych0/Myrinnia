package dev.ipsych0.mygame.skills;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingRecipe;

public abstract class Skill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int experience, level;
	protected int nextLevelXp = 100;
	protected Handler handler;
	protected ArrayList<SkillResource> resources;
	protected ArrayList<SkillCategory> categories;
	
	public Skill(Handler handler) {
		this.handler = handler;
		
		resources = new ArrayList<SkillResource>();
		categories = new ArrayList<SkillCategory>();
		experience = 0;
		level = 1;
	}

	public int getExperience() {
		return experience;
	}

	public void addExperience(int experience) {
		this.experience += experience;
		checkNextLevel();
	}

	public int getLevel() {
		return level;
	}

	public void addLevel() {
		this.level++;
	}
	
	private void checkNextLevel() {
		if(this.experience >= nextLevelXp) {
			experience -= nextLevelXp;
			addLevel();
			nextLevelXp = (int)(nextLevelXp * 1.1);
			checkNextLevel();
		}
	}

	public int getNextLevelXp() {
		return nextLevelXp;
	}

	public ArrayList<SkillResource> getResources() {
		return resources;
	}
	
	public ArrayList<SkillCategory> getCategories() {
		return categories;
	}

	public List<SkillResource> getListByCategory(SkillCategory category){
		List<SkillResource> subList = new ArrayList<SkillResource>();
		
		for(int i = 0; i < resources.size(); i++) {
			if(resources.get(i).getCategory() == category) {
				subList.add(resources.get(i));
			}
		}
		
		return subList;
	}

}
