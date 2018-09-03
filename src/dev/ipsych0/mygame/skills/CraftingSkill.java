package dev.ipsych0.mygame.skills;

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
