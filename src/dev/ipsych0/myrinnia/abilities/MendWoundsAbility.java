package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;

import java.awt.*;

public class MendWoundsAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = -7613939541331724237L;
    private int regenTimer;
    private double regenSeconds;
    private int baseHeal;
    private int regenHeal;
    private boolean initialHealDone = false;
    private Animation animation;


    public MendWoundsAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
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
        g.drawImage(Assets.mendWoundsI, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
    }

    @Override
    public void cast() {
        // Heal to max HP if current HP + baseHeal >= max HP already, otherwise add baseHeal
        if (!initialHealDone) {
            double baseHealLevelBoost = ((double) caster.getWaterLevel() * 5);
            double durationLevelBoost = ((double) caster.getWaterLevel() * 0.05);
            double regenLevelBoost = ((double) caster.getWaterLevel() * 1.33);

            // Round the seconds up
            regenSeconds = Math.ceil(5.0 + durationLevelBoost) * 60.0;
            baseHeal = 35 + (int) baseHealLevelBoost;
            regenHeal = 3 + (int) Math.round(regenLevelBoost);

            caster.heal(baseHeal);

            animation = new Animation(1000 / Assets.waterSplash1.length / 2, Assets.waterSplash1, true, true);
            initialHealDone = true;
            Handler.get().playEffect("abilities/mend_wounds.ogg", 0.1f);

//            // Remove all conditions
//            caster.getConditions().removeAll(caster.getConditions());
//
//            Buff b = new AttributeBuff(AttributeBuff.Attribute.INT, caster, 5, 10, true);
//            Buff b2 = new AttributeBuff(AttributeBuff.Attribute.STR, caster, 5, 10);
//            caster.addBuff(caster, caster, b);
//            caster.addBuff(caster, caster, b2);
        }

        animation.tick();

        // Regen
        regenTimer++;
        if (regenTimer == regenSeconds) {
            this.setCasting(false);
            animation = null;
        }

        if (regenTimer % 60 == 0) {
            // When we've reached the regen timer limit, the spell is done, set casting to false.
            caster.heal(regenHeal);
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
