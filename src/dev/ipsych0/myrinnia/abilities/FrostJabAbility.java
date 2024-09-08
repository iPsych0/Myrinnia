package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;


public class FrostJabAbility extends Ability implements Serializable {

    private boolean initialized = false;
    private Animation meleeAnimation;
    private double rotation, xPos, yPos;

    public FrostJabAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                           double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
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
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.frostJabI, x, y, null);
    }

    @Override
    public void cast() {
        if (!initialized) {

            Rectangle direction;
            if (caster.equals(Handler.get().getPlayer())) {
                direction = Handler.get().getMouse();
            } else {
                direction = new Rectangle((int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY(), 1, 1);
            }

            setMeleeSwing(direction);
            meleeAnimation = new Animation(48, Assets.regularMelee, true, false);

            Handler.get().playEffect("abilities/frost_jab.ogg", 0.1f);
            initialized = true;

            // Get the hit Entity
            Entity hit = getSingleMeleeHitEntity(direction);
            if (hit != null) {
                hit.damage(DamageType.STR, caster, this);

                // 0.07 seconds chill extra per water level
                double chillDurationLevelBoost = ((double) caster.getWaterLevel() * 0.07);

                // 20% Chance of chilling
                int rnd = Handler.get().getRandomNumber(1, 10);
                if (rnd <= 2) {
                    hit.addCondition(caster, new Condition(Condition.Type.CHILL, (3.0 + chillDurationLevelBoost)));
                }
            }
        }
    }

    @Override
    void reset() {
        initialized = false;
    }

    private void setMeleeSwing(Rectangle direction) {
        // The angle and speed of the projectile
        double angle;
        if (caster.equals(Handler.get().getPlayer())) {
            angle = Math.atan2((direction.getY() + Handler.get().getGameCamera().getyOffset() - 16) - caster.getY(), (direction.getX() + Handler.get().getGameCamera().getxOffset() - 16) - caster.getX());
        } else {
            angle = Math.atan2((direction.getY() - 16) - caster.getY(), (direction.getX() - 16) - caster.getX());
        }
        // Set the rotation of the projectile in degrees (0 = RIGHT, 270 = UP, 180 = LEFT, 90 = DOWN)
        rotation = Math.toDegrees(angle);
        if (rotation < 0) {
            rotation += 360d;
        }

        double xOffset = 1.0f * Math.cos(angle);
        double yOffset = 1.0f * Math.sin(angle);


        // meleeXOffset change RIGHT
        if (rotation >= 270 || rotation < 90) {
            xPos = (20d + (32d * (caster.getWidth() / 32d - 1))) * xOffset;
            // meleeXOffset change LEFT
        } else if (rotation >= 90 || rotation < 270) {
            xPos = (20d + (32d * (caster.getWidth() / 32d - 1))) * xOffset;
        }

        // meleeXOffset change UP
        if (rotation >= 180 || rotation <= 360) {
            yPos = (20d + (32d * (caster.getHeight() / 32d - 1))) * yOffset;
            // meleeXOffset change DOWN
        } else if (rotation >= 0 || rotation < 180) {
            yPos = (20d + (32d * (caster.getHeight() / 32d - 1))) * yOffset;
        }
    }

}
