package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;

public class CraftingSkill extends Skill {

	public CraftingSkill() {
		super();
		
		categories.add(SkillCategory.Leatherwork);
		categories.add(SkillCategory.CraftingOther);

	}

	@Override
	public String toString() {
		return "Crafting";
	}
}
