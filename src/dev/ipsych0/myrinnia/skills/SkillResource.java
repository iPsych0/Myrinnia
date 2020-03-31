package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.io.Serializable;

public class SkillResource implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 242384514442352183L;
    protected int levelRequirement;
    protected Item item;
    protected SkillCategory category;
    protected String description;

    public SkillResource(int requirement, Item item, SkillCategory category, String description) {
        this.levelRequirement = requirement;
        this.item = item;
        this.category = category;
        this.description = description;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
