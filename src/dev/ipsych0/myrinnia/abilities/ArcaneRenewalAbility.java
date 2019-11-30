package dev.ipsych0.myrinnia.abilities;

import java.awt.Graphics2D;
import java.io.Serializable;

import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;


public class ArcaneRenewalAbility extends Ability implements Serializable {

    public ArcaneRenewalAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                       double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        g.drawImage(Assets.mendWoundsI, x, y, null);
    }

    @Override
    public void cast() {

    }

}
