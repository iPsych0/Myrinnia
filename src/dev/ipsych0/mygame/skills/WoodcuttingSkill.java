package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.items.Item;

public class WoodcuttingSkill extends Skill {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2717227467657043794L;

	public WoodcuttingSkill() {

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
