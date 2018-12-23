package dev.ipsych0.myrinnia.abilities;

import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;


public class SupersonicDashAbility extends Ability implements Serializable {


    private static final long serialVersionUID = 5402188855951582419L;
    private int dashTime;
    private int dashTimeTimer;
    private boolean initialCast;
    private int maxDistance;
    private int speed;
    private double angle;
    private double xVelocity, yVelocity;

    public SupersonicDashAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                             double cooldownTime, double castingTime, double overcastTime, int baseDamage, int price, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, description);

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.purpleFlower1, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
    }

    @Override
    public void cast() {
        if (!initialCast) {
            maxDistance = 200;
            speed = 10;
            dashTime = maxDistance / speed;
            initialCast = true;
            angle = Math.atan2((Handler.get().getMouse().getY() + Handler.get().getGameCamera().getyOffset() - 16) - caster.getY(),
                    (Handler.get().getMouse().getX() + Handler.get().getGameCamera().getxOffset() - 16) - caster.getX());
            // The angle and speed of the dash
            xVelocity = speed * Math.cos(angle);
            yVelocity = speed * Math.sin(angle);
            Handler.get().getPlayer().setMovementAllowed(false);
        }

        dashTimeTimer++;

        if (dashTimeTimer >= dashTime) {
            setCasting(false);
            Handler.get().getPlayer().setMovementAllowed(true);
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
