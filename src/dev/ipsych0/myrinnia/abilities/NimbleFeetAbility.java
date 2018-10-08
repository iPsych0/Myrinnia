package dev.ipsych0.myrinnia.abilities;

import java.awt.Graphics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

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

        price = 2;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.bootsSlot, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
    }

    @Override
    public void cast() {
        if (!initialBoostDone) {
            baseMovementBoost = 1.0f;
            boostTime = 20 * 60;
            getCaster().setSpeed(getCaster().getSpeed() + baseMovementBoost);
            initialBoostDone = true;
            Handler.debugMode = true;
        }
        boostTimeTimer++;
        if (boostTimeTimer >= boostTime) {
            if (getCaster().getSpeed() - baseMovementBoost < Creature.DEFAULT_SPEED + 3.0f)
                getCaster().setSpeed(Creature.DEFAULT_SPEED + 3.0f);
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
