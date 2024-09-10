package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.crafting.CraftingRecipe;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.skills.Skill;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Celebration implements Serializable {

    private static final long serialVersionUID = -3830649550474412935L;
    private Skill skill;
    private Quest quest;
    private Ability ability;
    private CraftingRecipe recipe;
    private String description;
    private boolean pressedNext;

    public Celebration(Quest quest, String description) {
        this.quest = quest;
        this.description = description;
    }

    public Celebration(Skill skill, String description) {
        this.skill = skill;
        this.description = description;
    }

    public Celebration(Ability ability, String description) {
        this.ability = ability;
        this.description = description;
    }

    public Celebration(CraftingRecipe recipe, String description) {
        this.recipe = recipe;
        this.description = description;
    }

    public boolean hasPressedNext() {
        return pressedNext;
    }

    public void setPressedNext(boolean pressedNext) {
        this.pressedNext = pressedNext;
    }

    public BufferedImage getImg() {
        if (quest != null) {
            return Assets.questsIcon;
        }

        if (skill != null) {
            return skill.getImg();
        }

        if (recipe != null) {
            return recipe.getResult().getItem().getTexture();
        }

        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public CraftingRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(CraftingRecipe recipe) {
        this.recipe = recipe;
    }
}
