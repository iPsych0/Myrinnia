package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

public class CraftingSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = -8142804707921092774L;

    public CraftingSkill() {
        categories.add(SkillCategory.Leatherwork);
        categories.add(SkillCategory.CraftingOther);

    }

    @Override
    public String toString() {
        return "Crafting";
    }
}
