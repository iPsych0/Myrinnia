package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;

public class MiningSkill extends Skill {

	public MiningSkill(Handler handler) {
		super(handler);

		initResources();
		
	}
	
	private void initResources() {
		resources.add(new SkillResource(5, Item.oreItem, SkillCategory.Ores));
	}
	
	@Override
	public String toString() {
		return "Mining";
	}

}
