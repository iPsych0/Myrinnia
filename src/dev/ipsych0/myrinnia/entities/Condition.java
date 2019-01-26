package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Condition implements Serializable {

    private static final long serialVersionUID = -6491027693312163146L;
    protected Entity receiver;
    protected int duration, tickTimer;
    protected boolean active;
    protected int conditionDamage;
    private transient BufferedImage img;

    public Condition(Type type, Entity receiver, int durationSeconds) {
        this.img = type.getImg();
        this.receiver = receiver;
        this.duration = durationSeconds * 60;
        this.active = true;
    }

    public Condition(Type type, Entity receiver, int durationSeconds, int conditionDamage) {
        this.img = type.getImg();
        this.receiver = receiver;
        this.duration = durationSeconds * 60;
        this.conditionDamage = conditionDamage;
        this.active = true;
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
