package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;

public class FishingSkill extends Skill {

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
