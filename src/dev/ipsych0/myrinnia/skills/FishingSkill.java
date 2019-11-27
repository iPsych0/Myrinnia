package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class FishingSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = 6524654729089699242L;

    public FishingSkill() {
        super();

        initResources();

        categories.add(SkillCategory.Fish);

    }

    @Override
    public BufferedImage getImg() {
        return Assets.fishingIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.mackerelFish, SkillCategory.Fish));
    }

    @Override
    public String toString() {
        return "Fishing";
    }

}
