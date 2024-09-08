package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class SoulReapingSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = 5832865985482377808L;

    public SoulReapingSkill() {
        super();

        categories.add(SkillCategory.Souls);

        initResources();
    }

    @Override
    public BufferedImage getImg() {
        return Assets.soulReapingIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.lesserLumberjacksSoul, SkillCategory.Souls, "Can be released for a woodcutting buff."));
        resources.add(new SkillResource(1, Item.lesserMineworkersSoul, SkillCategory.Souls, "Can be released for a mining buff."));
        resources.add(new SkillResource(1, Item.lesserGardenersSoul, SkillCategory.Souls, "Can be released for a farming buff."));
        resources.add(new SkillResource(1, Item.lesserFishermansSoul, SkillCategory.Souls, "Can be released for a fishing buff."));
    }

    @Override
    public String toString() {
        return "Soul Reaping";
    }

}
