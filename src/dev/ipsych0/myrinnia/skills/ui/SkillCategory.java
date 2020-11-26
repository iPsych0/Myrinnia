package dev.ipsych0.myrinnia.skills.ui;

public enum SkillCategory {

    // Bounty hunter
    BountyTargets("Targets"),

    // Crafting
    CraftingOther("Other"),
    Weapons("Weapons"),
    Tools("Tools"),
    Recipes("Recipes"),
    Armor("Armor"),
    Trinkets("Trinkets"),
    Materials("Materials"),
    Potions("Potions"),

    // Fishing
    Fish("Fish"),

    // Mining
    Ores("Ores"),

    // Woodcutting
    Trees("Trees"),

    // Farming
    Vegetables("Vegetables"),
    BushPlants("Bush/Plants"),
    FarmingTrees("Fruit Trees");

    private String name;

    SkillCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
