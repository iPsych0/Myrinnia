package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.data.OnImpact;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class DebilitatingShotAbility extends Ability implements Serializable {

    private boolean initialized;
    private Animation animation;

    public DebilitatingShotAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
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
        g.drawImage(Assets.debilitatingShotI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {

    }

    @Override
    public void cast() {
        if (!initialized) {
            animation = new Animation(1000, Assets.debilitatingShotArrow, true);
            initialized = true;
        }

        Point target = getRangedTarget();
        if (target == null) {
            return;
        }
        int targetX = target.x;
        int targetY = target.y;

        Handler.get().playEffect("abilities/ranged_shot.ogg", 0.2f);
        new Projectile.Builder(DamageType.DEX, animation, caster, targetX, targetY)
                .withImpactSound("abilities/ranged_shot_impact2.ogg", 1.1f)
                .withAbility(this)
                .withVelocity(9.0f)
                .withImpact((Serializable & OnImpact) (receiver) ->
                        receiver.addCondition(caster, new Condition(Condition.Type.STUN, 2)))
                .build();

        setCasting(false);
    }

    @Override
    void reset() {
        initialized = false;
    }

}
