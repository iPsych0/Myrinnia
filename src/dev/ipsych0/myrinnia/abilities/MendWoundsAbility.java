package dev.ipsych0.myrinnia.abilities;

import java.awt.Graphics;

import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

public class MendWoundsAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = -7613939541331724237L;
    private int regenTimer;
    private int regenSeconds;
    private int baseHeal;
    private int regenHeal;
    private boolean initialHealDone = false;

    public MendWoundsAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                             double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.waterFlow1, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
    }

    @Override
    public void cast() {
        // Heal to max HP if current HP + baseHeal >= max HP already, otherwise add baseHeal
        if (!initialHealDone) {
            regenSeconds = 5 * 60;
            baseHeal = 30;
            regenHeal = 3;
            int heal = 0;
            this.caster.setHealth(heal = (this.caster.getHealth() + baseHeal >= this.caster.getMaxHealth()) ?
                    this.caster.getMaxHealth() : (this.caster.getHealth() + baseHeal));
            initialHealDone = true;
        }

        // Regen
        regenTimer++;
        if (regenTimer % 60 == 0) {
            // When we've reached the regen timer limit, the spell is done, set casting to false.
            if (regenTimer == regenSeconds) {
                this.setCasting(false);
            }
            int regen = 0;
            this.caster.setHealth(regen = (this.caster.getHealth() + regenHeal >= this.caster.getMaxHealth()) ?
                    this.caster.getMaxHealth() : (this.caster.getHealth() + regenHeal));
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
            regenTimer = 0;
            cooldownTimer = 0;
            initialHealDone = false;
        }
    }

}
