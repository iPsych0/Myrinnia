package dev.ipsych0.mygame.entities.npcs;

import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.shop.AbilityShopWindow;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbilityTrainer extends Creature implements Serializable {

    private static final long serialVersionUID = -7162393727931954900L;

    protected AbilityShopWindow abilityShopWindow;

    public AbilityTrainer(float x, float y, int width, int height) {
        super(x, y, width, height);

        attackable = false;
        isNpc = true;
        abilityShopWindow = new AbilityShopWindow(new ArrayList<Ability>());
    }

    public AbilityShopWindow getAbilityShopWindow() {
        return abilityShopWindow;
    }

    public void setAbilityShopWindow(AbilityShopWindow abilityShopWindow) {
        this.abilityShopWindow = abilityShopWindow;
    }
}
