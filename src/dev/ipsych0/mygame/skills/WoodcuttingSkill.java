package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;

public class WoodcuttingSkill extends Skill {

	public WoodcuttingSkill() {
		super();

		initResources();
		
		categories.add(SkillCategory.Trees);
	}
	
	private void initResources() {
		resources.add(new SkillResource(1, Item.regularLogs, SkillCategory.Trees));
	}
	
	@Override
	public String toString() {
		return "Woodcutting";
	}

}
