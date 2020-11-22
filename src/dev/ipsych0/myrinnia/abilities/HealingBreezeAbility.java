package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.buffs.AttributeBuff;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class HealingBreezeAbility extends Ability implements Serializable {

    private Animation animation;
    private boolean boostApplied;

    public HealingBreezeAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                                double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.healingBreezeI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null) {
            if (!animation.isTickDone()) {
                animation.tick();
                g.drawImage(animation.getCurrentFrame(),
                        (int) (caster.getX() + caster.getWidth() / 2 - 50 - Handler.get().getGameCamera().getxOffset()),
                        (int) (caster.getY() + caster.getHeight() / 2 - 80 - Handler.get().getGameCamera().getyOffset()), null);
            } else {
                animation = null;
            }
        }
    }

    @Override
    public void cast() {
        if (!boostApplied) {
            double statLevelBoost = ((double) caster.getAirLevel() * 0.02);
            double durationLevelBoost = ((double) caster.getAirLevel() * 0.2);
            double heal = ((double) caster.getAirLevel() * 7.5);
            double baseHeal = 40.0;

            // Heal caster and add movement speed buff
            caster.heal((int) (heal + baseHeal));
            caster.addBuff(caster, new AttributeBuff(AttributeBuff.Attribute.MOVSPD, caster, 3.0 + durationLevelBoost, 1.0 + statLevelBoost, true));

            animation = new Animation(1500 / Assets.healingBreeze.length, Assets.healingBreeze, true);
            boostApplied = true;

            // TODO: Add sound effect
            this.setCasting(false);
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
            boostApplied = false;
        }
    }

}
