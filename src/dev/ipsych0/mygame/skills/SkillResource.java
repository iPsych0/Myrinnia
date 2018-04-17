package dev.ipsych0.mygame.skills;

import java.io.Serializable;

import dev.ipsych0.mygame.items.Item;

public class SkillResource implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int levelRequirement;
	private Item item;
	private SkillCategory category;
	
	public SkillResource(int requirement, Item item, SkillCategory category) {
		this.levelRequirement = requirement;
		this.item = item;
		this.category = category;
	}

	public int getLevelRequirement() {
		return levelRequirement;
	}

	public void setLevelRequirement(int levelRequirement) {
		this.levelRequirement = levelRequirement;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}

}
