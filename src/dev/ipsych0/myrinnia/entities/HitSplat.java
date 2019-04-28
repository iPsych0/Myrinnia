package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class HitSplat implements Serializable {

    private static final long serialVersionUID = 7859808120479358201L;
    private Entity receiver;
    private int damage, health;
    private boolean active, healing;
    private int ty = 0;
    private int xOffset, yOffset;
    private transient Ability ability;

    HitSplat() {
    }

    public HitSplat(Entity receiver, int damage, DamageType damageType) {
        this.receiver = receiver;
        this.damage = damage;

        active = true;
        xOffset = Handler.get().getRandomNumber(-24, -12);
        yOffset = Handler.get().getRandomNumber(-12, 12);

    }

    public HitSplat(Entity receiver, int damage, Ability ability) {
        this.receiver = receiver;
        this.damage = damage;
        this.ability = ability;

        active = true;
        xOffset = Handler.get().getRandomNumber(-32, -16);
        yOffset = Handler.get().getRandomNumber(-12, 12);
    }

    public HitSplat(Entity receiver, int healing) {
        this.receiver = receiver;
        this.health = healing;
        this.healing = true;

        active = true;
        xOffset = Handler.get().getRandomNumber(0, 16);
        yOffset = Handler.get().getRandomNumber(-12, 12);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (active) {
            if (!healing) {
                if (ability != null) {
                    ability.render(g, (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + xOffset),
                            (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - ty + yOffset));
                }
                Text.drawString(g, String.valueOf(damage),
                        (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width / 2 + xOffset),
                        (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + 16 + receiver.height - ty + yOffset), false, Color.RED, Assets.font24);
            }else{
                Text.drawString(g, String.valueOf(health),
                        (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width / 2 + xOffset),
                        (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + 16 + receiver.height - ty + yOffset), false, Color.GREEN, Assets.font24);
            }
            ty++;

            if (ty >= 60) {
                active = false;
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

}
