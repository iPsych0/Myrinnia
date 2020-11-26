package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class MiningSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = -3083043178984091437L;

    public MiningSkill() {

        initResources();

        categories.add(SkillCategory.Ores);
    }

    @Override
    public BufferedImage getImg() {
        return Assets.miningIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.azuriteOre, SkillCategory.Ores, "Obtained by mining Azurite Rocks."));
        resources.add(new SkillResource(2, Item.copperOre, SkillCategory.Ores, "Obtained by mining Copper Rocks."));
        resources.add(new SkillResource(5, Item.ironOre, SkillCategory.Ores, "Obtained by mining Iron Rocks."));
        resources.add(new SkillResource(7, Item.tungstenOre, SkillCategory.Ores, "Obtained by mining Tungsten Rocks."));
        resources.add(new SkillResource(8, Item.clay, SkillCategory.Ores, "Obtained by mining Clay Rocks."));
        resources.add(new SkillResource(10, Item.coalOre, SkillCategory.Ores, "Obtained by mining Coal Rocks."));
        resources.add(new SkillResource(12, Item.silverOre, SkillCategory.Ores, "Obtained by mining Silver Rocks."));
        resources.add(new SkillResource(15, Item.platinumOre, SkillCategory.Ores, "Obtained by mining Platinum Rocks."));
        resources.add(new SkillResource(17, Item.goldOre, SkillCategory.Ores, "Obtained by mining Gold Rocks."));
        resources.add(new SkillResource(20, Item.titaniumOre, SkillCategory.Ores, "Obtained by mining Titanium Rocks."));
        resources.add(new SkillResource(22, Item.palladiumOre, SkillCategory.Ores, "Obtained by mining Palladium Rocks."));
        resources.add(new SkillResource(25, Item.obsidianShard, SkillCategory.Ores, "Obtained by mining Obsidian Rocks."));
        resources.add(new SkillResource(27, Item.cobaltOre, SkillCategory.Ores, "Obtained by mining Cobalt Rocks."));
    }

    @Override
    public String toString() {
        return "Mining";
    }

}
