package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.effects.ConditionOnHitReceivedEvent;
import dev.ipsych0.myrinnia.abilities.effects.EffectManager;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;
import java.util.List;


public class SepticBlastAbility extends Ability implements Serializable {

    private Rectangle hitBox;
    private boolean initDone;
    private int renderTimer;
    private int displayTime;
    private Animation animation;

    public SepticBlastAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                              double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.septicBlastI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null) {
            animation.tick();
            g.drawImage(animation.getCurrentFrame(),
                    (int) (caster.getX() - 48 - Handler.get().getGameCamera().getxOffset()),
                    (int) (caster.getY() - 48 - Handler.get().getGameCamera().getyOffset()),
                    hitBox.width, hitBox.height, null);
        }
    }

    @Override
    public void cast() {
        if (!initDone) {
            displayTime = 60;
            hitBox = new Rectangle((int) caster.getX() - 48, (int) caster.getY() - 48,
                    caster.getWidth() + 96, caster.getHeight() + 96);
            initDone = true;

            double baseDuration = 180d;
            double levelDuration = baseDuration + (caster.getEarthLevel() * 6);

            double baseCondiDmg = 3d;
            double levelCondiDmg = baseCondiDmg + (caster.getEarthLevel() * 2);

            double baseCondiDuration = 1d;
            double levelCondiDuration = baseCondiDuration + (caster.getEarthLevel() * 0.05);

            Handler.get().playEffect("abilities/eruption.ogg", 0.1f);

            animation = new Animation(1000 / Assets.septicBlast.length, Assets.septicBlast, true);

            // Hit all nearby enemies
            List<Entity> entities = getAllEntitiesInShape(hitBox);
            if (!entities.isEmpty()) {
                for (Entity e : entities) {
                    e.damage(DamageType.STR, caster, this);
                }
            }

            // Add lingering poison effect on hit received
            EffectManager.get().addEvent(caster, new ConditionOnHitReceivedEvent(Condition.Type.POISON, (int) levelCondiDmg, (int) levelCondiDuration, (int) levelDuration));

        }

        renderTimer++;
        if (renderTimer == displayTime) {
            hitBox = null;
            animation = null;
            setCasting(false);
        }
    }

    @Override
    void reset() {
        initDone = false;
        cooldownTimer = 0;
        renderTimer = 0;
        hitBox = null;
    }

}
