package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;

public interface AbilityTrainer {

    int RESET_COST = 1000;

    AbilityShopWindow getAbilityShopWindow();

    default void resetSkillPoints() {
        if (Handler.get().playerHasItem(Item.coins, RESET_COST)) {
            Handler.get().removeItem(Item.coins, RESET_COST);
            for (CharacterStats stat : CharacterStats.values()) {
                if (stat == CharacterStats.Combat)
                    continue;
                if (stat == CharacterStats.Magic || stat == CharacterStats.Melee || stat == CharacterStats.Ranged) {
                    Handler.get().getCharacterUI().addBaseStatPoints(stat.getLevel());
                } else {
                    Handler.get().getCharacterUI().addElementalStatPoints(stat.getLevel());
                }
                stat.setLevel(0);
            }
            Handler.get().sendMsg("Your Skill Points have been reset.");
        } else {
            Handler.get().sendMsg("You don't have enough gold to reset your Skill Points.");
        }
    }
}
