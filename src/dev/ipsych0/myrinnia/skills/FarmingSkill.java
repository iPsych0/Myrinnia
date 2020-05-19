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

        categories.add(SkillCategory.Vegetables);
        categories.add(SkillCategory.Bush);
        categories.add(SkillCategory.FarmingTrees);

    }

    @Override
    public BufferedImage getImg() {
        return Assets.farmingIcon;
    }

    private void initResources() {
        // Vegetables
        resources.add(new FarmingResource(1, Item.tomatoSeeds, 2, SkillCategory.Vegetables, 20L, Item.tomato, 4, 35, "Harvesting yields tomatoes."));
        resources.add(new FarmingResource(2, Item.cabbageSeeds, 2, SkillCategory.Vegetables, 25L, Item.cabbage, 4, 45, "Harvesting yields cabbages."));

        // Bush
        resources.add(new FarmingResource(2, Item.strawberrySeeds, 2, SkillCategory.Bush, 40L, Item.strawberry, 4, 45, "Harvesting yields strawberries."));
        resources.add(new FarmingResource(7, Item.raspberrySeeds, 2, SkillCategory.Bush, 80L, Item.raspberry, 4, 75, "Harvesting yields raspberries."));
        resources.add(new FarmingResource(14, Item.blackberrySeeds, 2, SkillCategory.Bush, 140L, Item.blackberry, 4, 135, "Harvesting yields blackberries."));
        resources.add(new FarmingResource(19, Item.blueberrySeeds, 2, SkillCategory.Bush, 240L, Item.blueberry, 4, 225, "Harvesting yields blueberries."));

        // Fruit trees
        resources.add(new FarmingResource(15, Item.appleTreeSeeds, 2, SkillCategory.FarmingTrees, 300L, Item.apple, 4, 250, "Harvesting yields apples."));
        resources.add(new FarmingResource(18, Item.bananaTreeSeeds, 2, SkillCategory.FarmingTrees, 420L, Item.banana, 4, 400, "Harvesting yields bananas."));
        resources.add(new FarmingResource(22, Item.orangeTreeSeeds, 2, SkillCategory.FarmingTrees, 540L, Item.orange, 4, 800, "Harvesting yields oranges."));
        resources.add(new FarmingResource(25, Item.apricotTreeSeeds, 2, SkillCategory.FarmingTrees, 660L, Item.apricot, 4, 1300, "Harvesting yields apricots."));
        resources.add(new FarmingResource(29, Item.peachTreeSeeds, 2, SkillCategory.FarmingTrees, 780L, Item.peach, 4, 2500, "Harvesting yields peaches."));
        resources.add(new FarmingResource(32, Item.papayaTreeSeeds, 2, SkillCategory.FarmingTrees, 900L, Item.papaya, 4, 3400, "Harvesting yields papayas."));
        resources.add(new FarmingResource(36, Item.starfruitTreeSeeds, 2, SkillCategory.FarmingTrees, 1020L, Item.starfruit, 4, 5200, "Harvesting yields starfruits."));
        resources.add(new FarmingResource(39, Item.dragonfruitTreeSeeds, 2, SkillCategory.FarmingTrees, 1140L, Item.dragonfruit, 4, 7100, "Harvesting yields dragonfruits."));
    }

    @Override
    public SkillResource getResourceByItem(Item item) {
        return resources
                .stream()
                .filter(x -> ((FarmingResource) x).getSeed().getId() == item.getId())
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Farming";
    }

}
