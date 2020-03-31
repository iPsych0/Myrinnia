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
        resources.add(new SkillResource(1, Item.mackerelFish, SkillCategory.Fish, "Can be caught in salt waters."));
        resources.add(new SkillResource(5, Item.trout, SkillCategory.Fish, "Can be caught in fresh waters."));
        resources.add(new SkillResource(7, Item.snakehead, SkillCategory.Fish, "Can be caught in subterranean waters."));
        resources.add(new SkillResource(10, Item.clam, SkillCategory.Fish, "Can be raked near coastlines."));
    }

    @Override
    public String toString() {
        return "Fishing";
    }

}
