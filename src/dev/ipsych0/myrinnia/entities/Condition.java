package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Condition implements Serializable {

    private static final long serialVersionUID = -6491027693312163146L;
    protected Entity receiver;
    protected int duration, tickTimer;
    protected boolean active;
    protected int conditionDamage;
    private transient BufferedImage img;
    private Type type;

    public Condition(Type type, Entity receiver, int durationSeconds) {
        this.type = type;
        this.img = type.getImg();
        this.receiver = receiver;
        this.duration = durationSeconds * 60;
        this.active = true;
    }

    public Condition(Type type, Entity receiver, int durationSeconds, int conditionDamage) {
        this.type = type;
        this.img = type.getImg();
        this.receiver = receiver;
        this.duration = durationSeconds * 60;
        this.conditionDamage = conditionDamage;
        this.active = true;
    }

    public void tick() {
        if (this.isActive()) {
            // If the enemy died, stop ticking, but finish the render of the last condition
            if (!receiver.isActive()) {
                if (tickTimer % 60 == 0) {
                    this.setActive(false);
                }
                tickTimer++;
                return;
            }

            // If the timeLeft is greater than 0 at any given time
            if (tickTimer <= duration) {
                // Tick the condition effect
                if (tickTimer == 0) {
                    duration -= 60;
                    receiver.tickCondition(receiver, this);
                } else if (tickTimer % 60 == 0) {
                    // After 1 second, recreate the damage splat
                    tickTimer = 0;
                    duration -= 60;
                    Handler.get().getWorld().getEntityManager().getHitSplats().add(new ConditionSplat(receiver, this, conditionDamage));
                    receiver.tickCondition(receiver, this);
                }
                // If the condition timeLeft is 0, don't tick anymore, but let the last hitsplat disappear
            } else if (duration <= 0) {
                if (tickTimer % 60 == 0) {
                    tickTimer = 0;
                    this.setActive(false);
                }
            }
            tickTimer++;
        }
    }

    public void render(Graphics g, int x, int y) {
        if (this.isActive()) {
            g.drawImage(img, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, String.valueOf((int) (duration / 60) + 1), x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getConditionDamage() {
        return conditionDamage;
    }

    public void setConditionDamage(int conditionDamage) {
        this.conditionDamage = conditionDamage;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        BURNING(Assets.fireballI), CHILL(Assets.mendWoundsI), BLEEDING(Assets.testSword), POISON(Assets.greenMushroom), STUN(Assets.undiscovered);

        Type(BufferedImage img) {
            this.img = img;
        }

        BufferedImage img;

        public BufferedImage getImg() {
            return img;
        }
    }
}
