package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.items.Item;

public class FishingSkill extends Skill {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6524654729089699242L;

	public FishingSkill() {
		super();
		
		initResources();
		
		categories.add(SkillCategory.Fish);
		
	}
	
	private void initResources() {
		resources.add(new SkillResource(1, Item.regularFish, SkillCategory.Fish));
	}
	
	@Override
	public String toString() {
		return "Fishing";
	}

}