package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class BountyHunterSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = 5832865985482377808L;

    public BountyHunterSkill() {
        super();

        categories.add(SkillCategory.Contracts);
        categories.add(SkillCategory.Targets);

        initResources();
    }

    @Override
    public BufferedImage getImg() {
        return Assets.bountyHunterIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.bountyContract, SkillCategory.Contracts, "Cut the Crab (Port Azure)"));
        resources.add(new SkillResource(2, Item.bountyContract, SkillCategory.Contracts, "It's mine (Shamrock Town)"));
        resources.add(new SkillResource(3, Item.bountyContract, SkillCategory.Contracts, "Heavy metal (Shamrock Town)"));
        resources.add(new SkillResource(5, Item.bountyContract, SkillCategory.Contracts, "Ruling with an iron fist (Shamrock Town)"));

        resources.add(new SkillResource(1, Item.coins, SkillCategory.Targets, "[target] (location)"));
    }

    @Override
    public String toString() {
        return "Bounty Hunter";
    }

}
