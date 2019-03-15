package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Skill implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2650558782741069411L;
    protected int experience, level;
    protected int nextLevelXp = 100;
    protected ArrayList<SkillResource> resources;
    protected ArrayList<SkillCategory> categories;

    public Skill() {
        resources = new ArrayList<SkillResource>();
        categories = new ArrayList<SkillCategory>();
        experience = 0;
        level = 1;
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int experience) {
        this.experience += experience;
        checkNextLevel();
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel() {
        this.level++;
    }

    protected void checkNextLevel() {
        if (experience >= nextLevelXp) {
            experience -= nextLevelXp;
            addLevel();
            nextLevelXp = (int) (nextLevelXp * 1.1);
            checkNextLevel();
            Player.isLevelUp = true;
            Handler.get().playEffect("ui/level_up.wav", 0.1f);
        }
    }

    public int getNextLevelXp() {
        return nextLevelXp;
    }

    public void setNextLevelXp(int nextLevelXp) {
        this.nextLevelXp = nextLevelXp;
    }

    public ArrayList<SkillResource> getResources() {
        return resources;
    }

    public ArrayList<SkillCategory> getCategories() {
        return categories;
    }

    public SkillResource getResourceByItem(Item item) {
        return resources
                .stream()
                .filter(x -> x.getItem().getId() == item.getId())
                .findAny()
                .orElse(null);
    }

    public List<SkillResource> getListByCategory(SkillCategory category) {
        return resources
                .stream()
                .filter(x -> x.getCategory() == category)
                .collect(Collectors.toList());
    }

}
