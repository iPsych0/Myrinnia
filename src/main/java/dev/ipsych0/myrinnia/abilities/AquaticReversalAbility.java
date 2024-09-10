package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class AquaticReversalAbility extends Ability implements Serializable {

    private boolean initialized;
    private Animation firstAnim, secondAnim;

    public AquaticReversalAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                                  double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.aquaticReversalI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        // Render the ability animation
    }

    @Override
    public void cast() {
        if (!initialized) {
            if (firstAnim == null) {
                firstAnim = new Animation(64, Assets.aquaticReversal, true);
            }
            if (secondAnim == null) {
                secondAnim = new Animation(64, Assets.aquaticReversal, true);
            }
            initialized = true;
        }
        Point target = getRangedTarget();
        if (target == null) {
            return;
        }
        int targetX = target.x;
        int targetY = target.y;

        // 4 extra health healed per water level
        double healingWaterBoost = ((double) caster.getWaterLevel() * 5);

        Handler.get().playEffect("abilities/aquatic_reversal.ogg", 0.1f);
        new Projectile.Builder(DamageType.INT, firstAnim, caster, targetX, targetY)
                .withVelocity(8.0f)
                .withAbility(this)
                .withImpactSound(null)
                .withImpact((receiver) -> {
                    Handler.get().playEffect("abilities/aquatic_reversal.ogg", 0.1f);
                    new Projectile.Builder(null, secondAnim, receiver, (int) caster.getX(), (int) caster.getY())
                            .withVelocity(6.0f)
                            .withImpactSound(null)
                            .withImpact((receiver2) -> {
                                caster.heal(12 + (int) healingWaterBoost);
                            }).build();
                }).build();

        setCasting(false);
    }

    @Override
    void reset() {
        initialized = false;
    }
}
