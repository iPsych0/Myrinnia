package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;

public class FishingSkill extends Skill {

	public FishingSkill(Handler handler) {
		super(handler);
		
		initResources();
		
	}
	
	private void initResources() {
		resources.add(new SkillResource(1, Item.coinsItem, SkillCategory.Fish));
	}
	
	@Override
	public String toString() {
		return "Fishing";
	}

}
