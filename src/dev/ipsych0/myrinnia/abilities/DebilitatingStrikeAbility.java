package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.data.MeleeDirection;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;


public class DebilitatingStrikeAbility extends Ability implements Serializable {

    private boolean initialized = false;
    private Animation meleeAnimation;
    private double rotation, xPos, yPos;
    private boolean hasHitEnemy, hasPlayedSound;
    private int timer, soundDelay = 45;

    public DebilitatingStrikeAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                                     double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.debilitatingStrikeI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (meleeAnimation != null) {
            if (meleeAnimation.isTickDone()) {
                meleeAnimation = null;
                setCasting(false);
            } else {
                meleeAnimation.tick();

                AffineTransform old = g.getTransform();
                g.rotate(Math.toRadians(rotation), (int) (caster.getX() + xPos + 32 / 2 - Handler.get().getGameCamera().getxOffset()), (int) (caster.getY() + yPos + 32 / 2 - Handler.get().getGameCamera().getyOffset()));
                g.drawImage(meleeAnimation.getCurrentFrame(), (int) (caster.getX() + xPos - Handler.get().getGameCamera().getxOffset()),
                        (int) (caster.getY() + yPos - Handler.get().getGameCamera().getyOffset()), (int) (32 * 1.25f), (int) (32 * 1.25f), null);
                g.setTransform(old);
            }
        }
    }

    @Override
    public void cast() {
        if (!initialized) {
            initialized = true;

            Rectangle direction;
            if (caster.equals(Handler.get().getPlayer())) {
                direction = Handler.get().getMouse();
            } else {
                direction = new Rectangle((int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 1, 1);
            }

            meleeAnimation = new Animation(48, Assets.regularMelee, true, false);
            Handler.get().playEffect("abilities/sword_swing.ogg", -0.05f);

            MeleeDirection meleeDirection = getMeleeSwing(direction);
            this.rotation = meleeDirection.getRotation();
            this.xPos = meleeDirection.getxPos();
            this.yPos = meleeDirection.getyPos();

            Entity hit = getSingleMeleeHitEntity(direction);

            if (hit != null) {
                hit.damage(DamageType.STR, caster, this);

                hasHitEnemy = true;
                double durationLevelBoost = ((double) caster.getEarthLevel() * 0.1);
                double damageLevelBoost = ((double) caster.getEarthLevel() * 1.5);

                // 50% Chance of bleeding
                int rnd = Handler.get().getRandomNumber(1, 100);
                if (rnd <= 50) {
                    hit.addCondition(caster, new Condition(Condition.Type.BLEEDING, 3.0 + durationLevelBoost, 5 + (int) damageLevelBoost));
                }
            }
        }

        if (hasHitEnemy) {
            timer++;
            if (!hasPlayedSound && timer >= soundDelay) {
                hasPlayedSound = true;
                Handler.get().playEffect("abilities/impact_medium.ogg", 0.1f);
            }
        }
    }

    @Override
    void reset() {
        initialized = false;
        hasHitEnemy = false;
        hasPlayedSound = false;
        timer = 0;
    }

}
