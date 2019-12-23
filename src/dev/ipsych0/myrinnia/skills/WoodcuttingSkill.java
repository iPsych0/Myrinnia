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
        resources.add(new SkillResource(1, Item.lightWood, SkillCategory.Trees));
        resources.add(new SkillResource(5, Item.hardWood, SkillCategory.Trees));
    }

    @Override
    public String toString() {
        return "Woodcutting";
    }

}
