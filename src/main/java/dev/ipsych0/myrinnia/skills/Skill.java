package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.publishers.SkillPublisher;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;
import dev.ipsych0.myrinnia.skills.ui.SkillResourceSlot;
import dev.ipsych0.myrinnia.ui.Celebration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Skill implements Serializable {

    private static final long serialVersionUID = 2650558782741069411L;
    int experience;
    int level;
    int nextLevelXp = 100;
    List<SkillResource> resources;
    List<SkillCategory> categories;

    protected Skill() {
        resources = new ArrayList<>();
        categories = new ArrayList<>();
        experience = 0;
        level = 1;
    }

    // Abstract methods
    public abstract BufferedImage getImg();

    void addLevel() {
        this.level++;
        SkillPublisher.get().publish(this);
        SkillPublisher.get().notifySubscribers();
    }

    void checkNextLevel() {
        if (experience >= nextLevelXp) {
            experience -= nextLevelXp;
            addLevel();
            nextLevelXp = (int) (nextLevelXp * 1.1);
            if (!Player.isLevelUp) {
                Handler.get().playEffect("ui/level_up.ogg", 0.1f);
            }
            Player.isLevelUp = true;
            checkNextLevel();
        } else {
            if (Player.isLevelUp) {
                Handler.get().getCelebrationUI().addEvent(new Celebration(this, this + " skill rose to level " + level + "!"));
                Handler.get().sendMsg(this + " skill rose to level " + level + "!");
                Player.isLevelUp = false;
            }
        }
    }

    public void addExperience(int experience) {
        Player.isXpGained = true;
        Player.expEffectPlayed = false;
        Player.xpGained = experience;
        this.experience += experience;
        Player.leveledSkill = this;
        checkNextLevel();
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

    public List<SkillResourceSlot> getSlotsByCategory(SkillCategory category, int startX, int startY) {
        List<SkillResourceSlot> slots = new ArrayList<>();
        List<SkillResource> recipes = getListByCategory(category);
        for (int i = 0; i < recipes.size(); i++) {
            SkillResource recipe = recipes.get(i);
            slots.add(new SkillResourceSlot(recipe, startX, startY + (i * ItemSlot.SLOTSIZE), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE));
        }
        return slots;
    }
}
