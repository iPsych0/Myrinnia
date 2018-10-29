package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.items.Item;

public class MiningSkill extends Skill {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3083043178984091437L;

	public MiningSkill() {
		
		initResources();
		
		categories.add(SkillCategory.Ores);
	}
	
	private void initResources() {
		resources.add(new SkillResource(1, Item.regularOre, SkillCategory.Ores));
	}
	
	@Override
	public String toString() {
		return "Mining";
	}

}
