package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.items.Item;

public class WoodcuttingSkill extends Skill {

	public WoodcuttingSkill(Handler handler) {
		super(handler);

		initResources();
		
	}
	
	private void initResources() {
		resources.add(new SkillResource(1, Item.woodItem, SkillCategory.Trees));
	}
	
	@Override
	public String toString() {
		return "Woodcutting";
	}

}
