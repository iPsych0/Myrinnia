package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.crafting.CraftingRecipe;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.skills.Skill;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.Serializable;

@Getter
@Setter
public class Celebration implements Serializable {

    private static final long serialVersionUID = -3830649550474412935L;
    private Skill skill;
    private Quest quest;
    private Ability ability;
    private CraftingRecipe recipe;
    private String description;

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
}
