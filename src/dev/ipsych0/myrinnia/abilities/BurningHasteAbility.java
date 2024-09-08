package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.effects.ConditionOnHitReceivedEvent;
import dev.ipsych0.myrinnia.abilities.effects.EffectManager;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.buffs.AttributeBuff;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class BurningHasteAbility extends Ability implements Serializable {

    private boolean initialBoostDone;
    private Animation animation;
    private int timer, maxTime;

    public BurningHasteAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                               double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.burningHasteI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null) {
            animation.tick();
            g.drawImage(animation.getCurrentFrame(),
                    (int) (caster.getX() - Handler.get().getGameCamera().getxOffset()),
                    (int) (caster.getY() - Handler.get().getGameCamera().getyOffset()),
                    32, 32, null);

            // Keep timer for rendering ability only while active
            timer++;
            if (timer >= maxTime) {
                animation = null;
            }
        }
    }

    @Override
    public void cast() {
        if (!initialBoostDone) {
            double baseMovementBoost = 1.0;
            double levelMovementBoost = baseMovementBoost + (caster.getFireLevel() * 0.02);

            double baseDuration = 180d;
            double levelDuration = baseDuration + (caster.getFireLevel() * 6);

            double baseCondiDmg = 3d;
            double levelCondiDmg = baseCondiDmg + (caster.getFireLevel() * 1.5);

            double baseCondiDuration = 1d;
            double levelCondiDuration = baseCondiDuration + (caster.getFireLevel() * 0.05);

            initialBoostDone = true;
            animation = new Animation(64, Assets.burningHaste);
            Handler.get().playEffect("abilities/nimble_feet.ogg", 0.1f);
            Buff b = new AttributeBuff(AttributeBuff.Attribute.MOVSPD, caster, (levelDuration / 60d), levelMovementBoost, true);
            caster.addBuff(caster, b);
            EffectManager.get().addEvent(caster, new ConditionOnHitReceivedEvent(Condition.Type.BURNING, (int) levelCondiDmg, (int) levelCondiDuration, (int) levelDuration));
            maxTime = (int) levelDuration;
        }

        setCasting(false);
    }

    @Override
    void reset() {
        initialBoostDone = false;
        maxTime = 0;
        timer = 0;
        animation = null;
    }

}
