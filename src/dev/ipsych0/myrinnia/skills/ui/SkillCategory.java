package dev.ipsych0.myrinnia.skills.ui;

public enum SkillCategory {

    CraftingOther("Other"),
    Weapons("Weapons"),
    Armor("Armor"),
    Trinkets("Trinkets"),
    Materials("Materials"),
    Potions("Potions"),
    Fish("Fish"),
    Ores("Ores"),
    Trees("Trees"),
    Vegetables("Vegetables"),
    Fruits("Fruits"),
    BountyTargets("Targets");

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
