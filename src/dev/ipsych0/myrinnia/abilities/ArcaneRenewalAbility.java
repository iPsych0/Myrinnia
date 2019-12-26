package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;
import java.io.Serializable;


public class ArcaneRenewalAbility extends Ability implements Serializable {

    private int baseHeal;
    private boolean initialHealDone = false;
    private Animation animation;

    public ArcaneRenewalAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
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
        g.drawImage(Assets.arcaneRenewalI, x, y, null);
    }

    @Override
    public void cast() {
        if (!initialHealDone) {
            baseHeal = 40;
            caster.heal(baseHeal);

            animation = new Animation(1000 / Assets.waterSplash1.length / 2, Assets.waterSplash1, true, true);

            initialHealDone = true;
            Handler.get().playEffect("abilities/arcane_renewal.wav", 0.1f);

            // Remove the first applied condition
            if (!caster.getConditions().isEmpty()) {
                caster.getConditions().remove(0);
            }

        }

        animation.tick();

        if (animation.isTickDone()) {
            this.setCasting(false);
            animation = null;
        }

    }

    @Override
    protected void countDown() {
        cooldownTimer++;
        if (cooldownTimer / 60 == cooldownTime) {
            this.setOnCooldown(false);
            this.setActivated(false);
            this.setCasting(false);
            castingTimeTimer = 0;
            cooldownTimer = 0;
            initialHealDone = false;
        }
    }

}
