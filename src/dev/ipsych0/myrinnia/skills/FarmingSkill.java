package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class FarmingSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = 6524654729089699242L;

    public FarmingSkill() {
        super();

        initResources();

        categories.add(SkillCategory.Fruits);
        categories.add(SkillCategory.Vegetables);

    }

    @Override
    public BufferedImage getImg() {
        return Assets.farmingIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.vineRoot, SkillCategory.Fruits));
    }

    @Override
    public String toString() {
        return "Farming";
    }

}
