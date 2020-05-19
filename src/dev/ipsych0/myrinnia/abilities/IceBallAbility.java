package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class IceBallAbility extends Ability implements Serializable {

    public IceBallAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                          double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {

    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.iceBallI, x, y, null);
    }

    @Override
    public void cast() {

        Point target = getRangedTarget();
        if (target == null) {
            return;
        }
        int targetX = target.x;
        int targetY = target.y;

        // 0.08 seconds chill extra per water level
        double chillDurationLevelBoost = ((double) caster.getWaterLevel() * 0.08);

        Handler.get().playEffect("abilities/ice_ball.ogg", 0.1f);
        new Projectile.Builder(DamageType.INT, Assets.iceBall1, caster, targetX, targetY)
                .withVelocity(7.0f)
                .withAbility(this)
                .withImpactSound("abilities/ice_projectile_impact.ogg")
                .withImpact((c) -> {
                    c.addCondition(caster, new Condition(Condition.Type.CHILL, (3.0 + chillDurationLevelBoost)));
                }).build();

        setCasting(false);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
    }

    @Override
    public void countDown() {
        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            cooldownTimer = 0;
        }
    }

}
