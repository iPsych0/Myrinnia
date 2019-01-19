package dev.ipsych0.myrinnia.abilities;

import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;


public class SupersonicDashAbility extends Ability implements Serializable {


    private static final long serialVersionUID = 5402188855951582419L;
    private int dashTime;
    private int dashTimeTimer;
    private boolean initialCast;
    private double xVelocity, yVelocity;
    private Animation animation;

    public SupersonicDashAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                             double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.supersonicDashI, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
        if(animation != null && !animation.isTickDone()){
            g.drawImage(animation.getCurrentFrame(),
                    (int) (caster.getX() - Handler.get().getGameCamera().getxOffset()),
                    (int) (caster.getY() - Handler.get().getGameCamera().getyOffset()),
                    32, 32, null);
        }
    }

    @Override
    public void cast() {
        if (!initialCast) {
            int maxDistance = 200;
            int speed = 10;
            dashTime = maxDistance / speed;
            initialCast = true;

            // Calculate the direction in which to move
            double angle = Math.atan2((Handler.get().getMouse().getY() + Handler.get().getGameCamera().getyOffset() - 16) - caster.getY(),
                    (Handler.get().getMouse().getX() + Handler.get().getGameCamera().getxOffset() - 16) - caster.getX());
            // The angle and speed of the dash
            xVelocity = speed * Math.cos(angle);
            yVelocity = speed * Math.sin(angle);
            Handler.get().getPlayer().setMovementAllowed(false);

            // Animation speed
            double framesPerSecond = dashTime / 60.0; // Percentage of time traveled per tick (0.4)
            double animationSpeed = (1000 / Assets.airCloud1.length) * framesPerSecond; // 7 frames per second (1000)
            animation = new Animation((int)animationSpeed, Assets.airCloud1, true);
            Handler.get().playEffect("abilities/supersonic_dash.wav");
        }

        animation.tick();

        dashTimeTimer++;

        if (dashTimeTimer >= dashTime) {
            setCasting(false);
            Handler.get().getPlayer().setMovementAllowed(true);
            animation = null;
        }

        caster.setxMove((float)xVelocity);
        caster.setyMove((float)yVelocity);

        caster.move();

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
            initialCast = false;
            dashTimeTimer = 0;
        }
    }

}
