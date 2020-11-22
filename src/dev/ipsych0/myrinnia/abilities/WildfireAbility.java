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
import java.io.Serializable;
import java.util.List;


public class WildfireAbility extends Ability implements Serializable {

    private Animation animation;
    private boolean initialized;
    private Rectangle hitBox;
    private int timer;
    private static final int NUM_HITS = 5, HIT_INTERVAL = 30;
    private static final int HIT_TIMER = NUM_HITS * HIT_INTERVAL; // Hit once each 0.5 frame for 5x total

    public WildfireAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                           double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);
    }

    @Override
    public void renderIcon(Graphics2D g, int x, int y) {
        g.drawImage(Assets.wildfireI, x, y, null);
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (animation != null && !animation.isTickDone()) {
            animation.tick();
            g.drawImage(animation.getCurrentFrame(),
                    (int) (hitBox.x - Handler.get().getGameCamera().getxOffset()),
                    (int) (hitBox.y - Handler.get().getGameCamera().getyOffset()),
                    hitBox.width, hitBox.height, null);
        }
    }

    @Override
    public void cast() {
        if (!initialized) {
            hitBox = new Rectangle((int) caster.getX() - 48, (int) caster.getY() - 48,
                    caster.getWidth() + 96, caster.getHeight() + 96);
            initialized = true;

            // TODO: Add sound effect!

            animation = new Animation(2500 / Assets.wildfire.length, Assets.wildfire, true);
        }

        timer++;
        if (timer % HIT_INTERVAL == 0) {
            List<Entity> entities = getAllEntitiesInShape(hitBox);
            if (!entities.isEmpty()) {
                for (Entity e : entities) {
                    e.damage(DamageType.STR, caster, this);
                    int rnd = Handler.get().getRandomNumber(1, 10);
                    if (rnd == 1) {
                        e.addCondition(caster, new Condition(Condition.Type.BURNING, 3, 5));
                    }
                }
            }

            if (timer >= HIT_TIMER) {
                setCasting(false);
            }
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
            initialized = false;
            timer = 0;
        }
    }

}
