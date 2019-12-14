package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;
import dev.ipsych0.myrinnia.ui.Celebration;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Skill implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2650558782741069411L;
    int experience;
    int level;
    int nextLevelXp = 100;
    ArrayList<SkillResource> resources;
    ArrayList<SkillCategory> categories;

    Skill() {
        resources = new ArrayList<>();
        categories = new ArrayList<>();
        experience = 0;
        level = 1;
    }

    // Abstract methods
    public abstract BufferedImage getImg();

    // Getter + setter logic

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    void addLevel() {
        this.level++;
    }

    void checkNextLevel() {
        if (experience >= nextLevelXp) {
            experience -= nextLevelXp;
            addLevel();
            nextLevelXp = (int) (nextLevelXp * 1.1);
            if (!Player.isLevelUp) {
                Handler.get().playEffect("ui/level_up.wav", 0.1f);
            }
            Player.isLevelUp = true;
            checkNextLevel();
        } else {
            if (Player.isLevelUp) {
                Handler.get().getCelebrationUI().addEvent(new Celebration(this, toString() + " skill rose to level " + this.getLevel() + "!"));
            }
        }
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int experience) {
        Player.isXpGained = true;
        Player.expEffectPlayed = false;
        Player.xpGained = experience;
        this.experience += experience;
        Player.leveledSkill = this;
        checkNextLevel();
    }

    public void setExperience(int experience) {
        this.experience = experience;
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
