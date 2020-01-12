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
        resources.add(new FarmingResource(1, Item.tomatoSeeds, 3, SkillCategory.Vegetables, 30L, Item.tomato));
        resources.add(new FarmingResource(1, Item.cabbageSeeds, 3, SkillCategory.Vegetables, 30L, Item.cabbage));
    }

    @Override
    public SkillResource getResourceByItem(Item item) {
        return resources
                .stream()
                .filter(x -> ((FarmingResource)x).getSeed().getId() == item.getId())
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Farming";
    }

}
