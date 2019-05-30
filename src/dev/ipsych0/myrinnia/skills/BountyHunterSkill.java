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

        categories.add(SkillCategory.BountyTargets);

        initResources();
    }

    @Override
    public BufferedImage getImg() {
        return Assets.bountyHunterIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.coins, SkillCategory.BountyTargets));
    }

    @Override
    public String toString() {
        return "Bounty Hunter";
    }

}
