package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.data.OnImpact;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class GlacialShotAbility extends Ability implements Serializable {

    private Animation animation;
    private boolean initialized;

    public GlacialShotAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                              double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {

    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.glacialShotI, x, y, null);
    }

    @Override
    public void cast() {
        if (!initialized) {
            animation = new Animation(600 / Assets.glacialShot1.length, Assets.glacialShot1, true, false);
            initialized = true;
        }

        Point target = getRangedTarget();
        if (target == null) {
            return;
        }
        int targetX = target.x;
        int targetY = target.y;

        // 0.075 seconds chill extra per water level
        double chillDurationLevelBoost = ((double) caster.getWaterLevel() * 0.075);

        Handler.get().playEffect("abilities/glacial_shot.ogg", 0.1f);
        new Projectile.Builder(DamageType.DEX, animation, caster, targetX, targetY)
                .withImpactSound("abilities/ice_projectile_impact.ogg")
                .withAbility(this)
                .withVelocity(7.0f)
                .withImpact((Serializable & OnImpact) (receiver) ->
                        receiver.addCondition(caster, new Condition(Condition.Type.CHILL, (3.0 + chillDurationLevelBoost))))
                .build();

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
            initialized = false;
        }
    }

}
