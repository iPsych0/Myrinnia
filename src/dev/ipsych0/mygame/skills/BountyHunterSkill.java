package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;

public class BountyHunterSkill extends Skill {

	public BountyHunterSkill(Handler handler) {
		super(handler);
		
		categories.add(SkillCategory.BountyTargets);
		
		initResources();
	}
	
	private void initResources() {
		resources.add(new SkillResource(1, Item.coins, SkillCategory.BountyTargets));
	}
	
	@Override
	public String toString() {
		return "Bounty Hunter";
	}

}
