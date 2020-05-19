package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.data.OnImpact;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class SiphoningShotAbility extends Ability implements Serializable {

    public SiphoningShotAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
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
        g.drawImage(Assets.siphoningShotI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        // Render the ability animation
    }

    @Override
    public void cast() {

        Point target = getRangedTarget();
        if (target == null) {
            return;
        }
        int targetX = target.x;
        int targetY = target.y;

        // 4 extra health healed per water level
        double healingWaterBoost = ((double) caster.getWaterLevel() * 4);

        Handler.get().playEffect("abilities/ranged_shot.ogg", 0.15f);
        new Projectile.Builder(DamageType.DEX, Assets.siphoningShot, caster, targetX, targetY)
                .withImpactSound("abilities/siphoning_shot_impact.ogg", 0.4f)
                .withAbility(this)
                .withVelocity(9.0f)
                .withImpact((Serializable & OnImpact) (receiver) ->
                        caster.heal(10 + (int) healingWaterBoost))
                .build();

        setCasting(false);
    }
}
