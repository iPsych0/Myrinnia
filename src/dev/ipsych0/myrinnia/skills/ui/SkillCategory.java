package dev.ipsych0.myrinnia.skills.ui;

public enum SkillCategory {

    Leatherwork("Leatherwork"), CraftingOther("Other"), Weapons("Weapons"), Fish("Fish"), Ores("Ores"), Trees("Trees"), BountyTargets("Targets");

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
