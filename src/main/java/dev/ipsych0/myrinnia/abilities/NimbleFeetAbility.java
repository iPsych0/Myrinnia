package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.buffs.AttributeBuff;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;

import java.awt.*;

public class NimbleFeetAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = 6275665878660555382L;
    private double baseMovementBoost;
    private boolean initialBoostDone;
    private Animation animation;

    public NimbleFeetAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                             double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);

    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null && !animation.isTickDone()) {
            g.drawImage(animation.getCurrentFrame(),
                    (int) (caster.getX() - Handler.get().getGameCamera().getxOffset()),
                    (int) (caster.getY() - Handler.get().getGameCamera().getyOffset()),
                    32, 32, null);
        }
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.nimbleFeetI, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
    }

    @Override
    public void cast() {
        if (!initialBoostDone) {
            baseMovementBoost = 1.0;
            initialBoostDone = true;
            animation = new Animation(1000 / Assets.movementBoost1.length, Assets.movementBoost1, true);
            Handler.get().playEffect("abilities/nimble_feet.ogg", 0.1f);
            Buff b = new AttributeBuff(AttributeBuff.Attribute.MOVSPD, caster, 5, baseMovementBoost, true);
            caster.addBuff(caster, b);
        }

        animation.tick();

        if (animation.isTickDone()) {
            setCasting(false);
            animation = null;
        }

    }

    @Override
    void reset() {
        initialBoostDone = false;
    }

}
