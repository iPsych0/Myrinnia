package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

import java.awt.*;

public class NimbleFeetAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = 6275665878660555382L;
    private float baseMovementBoost;
    private int boostTime;
    private int boostTimeTimer;
    private boolean initialBoostDone;

    public NimbleFeetAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                             double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.bootsSlot, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
    }

    @Override
    public void cast() {
        if (!initialBoostDone) {
            baseMovementBoost = 1.0f;
            boostTime = 5 * 60;
            getCaster().setSpeed(getCaster().getSpeed() + baseMovementBoost);
            initialBoostDone = true;
        }
        boostTimeTimer++;
        if (boostTimeTimer >= boostTime) {
            if (getCaster().getSpeed() - baseMovementBoost <= Creature.DEFAULT_SPEED + 1.0f)
                getCaster().setSpeed(Creature.DEFAULT_SPEED + 1.0f);
            else
                getCaster().setSpeed(getCaster().getSpeed() - baseMovementBoost);

            setCasting(false);
        }

    }

    @Override
    protected void countDown() {
        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            cooldownTimer = 0;
            initialBoostDone = false;
            boostTimeTimer = 0;
        }
    }

}
