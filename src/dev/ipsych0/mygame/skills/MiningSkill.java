package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;

public class MiningSkill extends Skill {

	public MiningSkill() {
		super();

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
