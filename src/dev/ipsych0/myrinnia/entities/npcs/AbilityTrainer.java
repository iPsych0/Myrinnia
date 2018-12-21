package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;

import java.io.Serializable;

public abstract class AbilityTrainer extends Creature implements Serializable {

    private static final long serialVersionUID = -7162393727931954900L;

    public static int resetCost = 1000;

    protected AbilityShopWindow abilityShopWindow;

    public AbilityTrainer(float x, float y, int width, int height) {
        super(x, y, width, height);

        attackable = false;
        isNpc = true;
    }

    public AbilityShopWindow getAbilityShopWindow() {
        return abilityShopWindow;
    }

    public void setAbilityShopWindow(AbilityShopWindow abilityShopWindow) {
        this.abilityShopWindow = abilityShopWindow;
    }

    public void resetSkillPoints() {
        if (Handler.get().playerHasItem(Item.coins, resetCost)) {
            Handler.get().removeItem(Item.coins, resetCost);
            for (CharacterStats stat : CharacterStats.values()) {
                if (stat == CharacterStats.Magic || stat == CharacterStats.Melee || stat == CharacterStats.Ranged) {
                    Handler.get().getCharacterUI().addBaseStatPoints(stat.getLevel());
                } else {
                    Handler.get().getCharacterUI().addElementalStatPoints(stat.getLevel());
                }
                stat.setLevel(0);
            }
            resetCost *= 2;
            Handler.get().sendMsg("Your Skill Points have been reset.");
        } else {
            Handler.get().sendMsg("You don't have enough gold to reset your Skill Points.");
        }
    }
}
