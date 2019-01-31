package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Buff {

    protected Entity receiver;
    protected int duration, tickTimer;
    protected boolean active;
    private transient BufferedImage img;
    private Type type;

    public Buff(Type type, Entity receiver, int durationSeconds) {
        this.receiver = receiver;
        this.duration = durationSeconds * 60;
        this.type = type;
        this.img = type.getImg();
        this.active = true;
    }

    public void tick() {
        if (this.isActive()) {
            // If the enemy died, stop ticking
            if (!receiver.isActive()) {

                this.setActive(false);
                return;
            }

            // If the duration is greater than 0 at any given time
            if (tickTimer <= duration) {
                // Tick the buff effect
                if (tickTimer == 0) {
                    duration -= 60;
                    receiver.tickBuff(receiver, this);
                } else if (tickTimer % 60 == 0) {
                    // After 1 second, tick the buff effect
                    tickTimer = 0;
                    duration -= 60;
                    ((Creature) receiver).setStrength(((Creature) receiver).getStrength() - 10);
                    receiver.tickBuff(receiver, this);
                }
                // If the duration expired, don't tick anymore
            } else if (duration <= 0) {
                ((Creature) receiver).setStrength(((Creature) receiver).getStrength() - 10);
                this.setActive(false);
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

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        STR(Assets.testSword), DEX(Assets.emptyCrate), INT(Assets.fireballI), VIT(Assets.ore),
        DEF(Assets.fishingIcon), ATKSPD(Assets.dirtHole), MOVSPD(Assets.bootsSlot);

        Type(BufferedImage img) {
            this.img = img;
        }

        BufferedImage img;

        public BufferedImage getImg() {
            return this.img;
        }
    }
}
