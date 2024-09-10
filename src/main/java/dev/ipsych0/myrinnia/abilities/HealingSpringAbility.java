package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.abilities.data.PBAoECircle;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class HealingSpringAbility extends Ability implements Serializable {

    private boolean initialHealDone = false;
    private boolean springActive;
    private boolean standingOn;
    private int springTimer;
    private double springTimeActive;
    private int regenTimer;
    private double regenSeconds;
    private int baseHeal;
    private int regenHeal;
    private Animation animation;
    private PBAoECircle pbAoECircle;
    private static Color springColor = new Color(26, 93, 139, 172);

    public HealingSpringAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                                double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null && !animation.isTickDone()) {
            g.drawImage(animation.getCurrentFrame(),
                    (int) (caster.getX() - Handler.get().getGameCamera().getxOffset()),
                    (int) (caster.getY() - Handler.get().getGameCamera().getyOffset()),
                    32, 32, null);
        }
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.healingSpringI, x, y, null);
    }

    @Override
    public void renderUnderEntity(Graphics2D g) {
        if (springActive && pbAoECircle != null) {
            g.setColor(springColor);
            g.fillOval((int) (pbAoECircle.x - Handler.get().getGameCamera().getxOffset()), (int) (pbAoECircle.y - Handler.get().getGameCamera().getyOffset()), (int) pbAoECircle.width, (int) pbAoECircle.height);
        }
    }

    @Override
    public void cast() {
        if (!initialHealDone) {
//            EffectManager.get().addEvent(caster, new ConditionOnHitEvent(Condition.Type.POISON, 3, 5, 10, 3));
            springActive = true;
            pbAoECircle = new PBAoECircle(caster, 80);

            double baseHealLevelBoost = ((double) caster.getWaterLevel() * 5);
            double durationLevelBoost = ((double) caster.getWaterLevel() * 0.05);
            double regenLevelBoost = ((double) caster.getWaterLevel() * 1.5);

            // Round the seconds up
            regenSeconds = Math.ceil(5.0 + durationLevelBoost) * 60.0;
            baseHeal = 35 + (int) baseHealLevelBoost;
            regenHeal = 3 + (int) regenLevelBoost;
            springTimeActive = Math.ceil(5.0 + durationLevelBoost) * 60.0;

            caster.heal(baseHeal);

            animation = new Animation(1000 / Assets.waterSplash1.length / 2, Assets.waterSplash1, true, true);

            initialHealDone = true;
            Handler.get().playEffect("abilities/healing_spring.ogg", 0.1f);

            // Remove the first applied condition
            if (!caster.getConditions().isEmpty()) {
                caster.getConditions().remove(0);
            }

        }

        animation.tick();

        // Check if we're standing in the spring
        standingOn = caster.getCollisionBounds(0, 0).intersects(pbAoECircle.getBounds());

        // If it's active and we're standing in it, start counting the regen timer
        if (springActive && standingOn) {
            // Regen
            regenTimer++;
            if (regenTimer % 60 == 0) {
                // When we've reached the regen timer limit, the spell is done, set casting to false.
                if (regenTimer >= regenSeconds) {
                    this.setCasting(false);
                    animation = null;
                }
                caster.heal(regenHeal);
            }
        }

        if (springActive) {
            springTimer++;
            if (springTimer >= springTimeActive) {
                springActive = false;
                this.setCasting(false);
            }
        }
    }

    @Override
    void reset() {
        regenTimer = 0;
        cooldownTimer = 0;
        initialHealDone = false;
        springActive = false;
        standingOn = false;
        springTimer = 0;
    }

}
