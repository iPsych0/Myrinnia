package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.buffs.AttributeBuff;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class NaturalResilienceAbility extends Ability implements Serializable {

    private Animation animation;
    private boolean boostApplied;

    public NaturalResilienceAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                                    double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.naturalResilienceI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null) {
            if (!animation.isTickDone()) {
                animation.tick();
                g.drawImage(animation.getCurrentFrame(),
                        (int) (caster.getX() - 16 - Handler.get().getGameCamera().getxOffset()),
                        (int) (caster.getY() - 16 - Handler.get().getGameCamera().getyOffset()),
                        caster.getWidth() + 32, caster.getHeight() + 32, null);
            } else {
                animation = null;
            }
        }
    }

    @Override
    public void cast() {
        if (!boostApplied) {
            double statLevelBoost = ((double) caster.getEarthLevel() * 5);
            double durationLevelBoost = ((double) caster.getEarthLevel() * 0.3);

            caster.addBuff(caster, new AttributeBuff(AttributeBuff.Attribute.DEF, caster, 6.0 + durationLevelBoost, 12.0 + statLevelBoost, true));
            animation = new Animation(1500 / Assets.bulkBarrierResilience.length, Assets.bulkBarrierResilience, true);
            boostApplied = true;

            Handler.get().playEffect("abilities/bulk_barrier_resilience.ogg", 0.1f);
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
