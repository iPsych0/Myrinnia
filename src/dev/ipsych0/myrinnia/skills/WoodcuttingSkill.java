package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class WoodcuttingSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = 2717227467657043794L;

    public WoodcuttingSkill() {

        initResources();

        categories.add(SkillCategory.Trees);
    }

    @Override
    public BufferedImage getImg() {
        return Assets.woodcuttingIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.palmWood, SkillCategory.Trees, "Obtained by cutting Weak Palm Trees."));
        resources.add(new SkillResource(2, Item.lightwood, SkillCategory.Trees, "Obtained by cutting Elm Trees."));
        resources.add(new SkillResource(5, Item.hardWood, SkillCategory.Trees, "Obtained by cutting Oak Trees."));
        resources.add(new SkillResource(10, Item.aspenwood, SkillCategory.Trees, "Obtained by cutting Aspenwood Trees."));
    }

    @Override
    public String toString() {
        return "Woodcutting";
    }

}
