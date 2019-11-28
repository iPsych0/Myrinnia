package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class CraftingSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = -8142804707921092774L;

    public CraftingSkill() {
        categories.add(SkillCategory.Weapons);
        categories.add(SkillCategory.Leatherwork);
        categories.add(SkillCategory.CraftingOther);

    }

    @Override
    public BufferedImage getImg() {
        return Assets.craftingIcon;
    }

    @Override
    public String toString() {
        return "Crafting";
    }
}
