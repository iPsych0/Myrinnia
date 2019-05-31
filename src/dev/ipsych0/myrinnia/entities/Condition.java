package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Condition implements Serializable {

    private static final long serialVersionUID = -6491027693312163146L;
    private Entity receiver;
    private int currentDuration;
    private int initialDuration;
    private int tickTimer;
    private boolean active;
    private int conditionDamage;
    private transient BufferedImage img;
    private float initialSpeedDecrease;
    private static final double CHILL_MOVSPD = 0.66;
    private Type type;

    public Condition(Type type, Entity receiver, int durationSeconds) {
        this.type = type;
        this.img = type.getImg();
        this.receiver = receiver;
        this.currentDuration = durationSeconds * 60;
        this.initialDuration = currentDuration;
        this.active = true;
    }

    public Condition(Type type, Entity receiver, int durationSeconds, int conditionDamage) {
        this.type = type;
        this.img = type.getImg();
        this.receiver = receiver;
        this.currentDuration = durationSeconds * 60;
        this.initialDuration = currentDuration;
        this.conditionDamage = conditionDamage;
        this.active = true;
    }

    public void tick() {
        if (this.isActive()) {
            // If the enemy died, stop ticking, but finish the render of the last condition
            if (!receiver.isActive()) {
                if (tickTimer % 60 == 0) {
                    clear();
                }
                tickTimer++;
                return;
            }

            // If the timeLeft is greater than 0 at any given time
            if (tickTimer <= currentDuration) {
                // Tick the condition effect
                if (tickTimer == 0) {
                    currentDuration -= 60;
                    apply();
                } else if (tickTimer % 60 == 0) {
                    update();
                }
                // If the condition timeLeft is 0, don't tick anymore, but let the last hitsplat disappear
            } else if (currentDuration <= 0) {
                if (tickTimer % 60 == 0) {
                    clear();
                }
            }
            tickTimer++;
        }
    }

    public void render(Graphics2D g, int x, int y) {
        if (this.isActive()) {
            g.drawImage(img, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, String.valueOf(currentDuration / 60 + 1), x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }

    private void apply() {
        receiver.tickCondition(receiver, this);
        if(type == Type.CHILL){
            Creature r = ((Creature)receiver);
            float currMovSpd = r.getSpeed();
            float newMovSpd = (float)(r.getSpeed() * CHILL_MOVSPD);
            initialSpeedDecrease = currMovSpd - newMovSpd;
            r.setSpeed(newMovSpd);
        }
    }

    private void update() {
        // After 1 second, recreate the damage splat
        tickTimer = 0;
        currentDuration -= 60;
        Handler.get().getWorld().getEntityManager().getHitSplats().add(new ConditionSplat(receiver, this, conditionDamage));
        receiver.tickCondition(receiver, this);
    }

    public void clear() {
        tickTimer = 0;
        this.setActive(false);

        if(type == Type.CHILL){
            Creature r = ((Creature)receiver);
            r.setSpeed(r.getSpeed() + initialSpeedDecrease);
        }
    }

    public int getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(int currentDuration) {
        this.currentDuration = currentDuration;
    }

    public boolean isActive() {
        return active;
    }

    private void setActive(boolean active) {
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
        BURNING(Assets.burnIcon,"'Burning' inflicts damage over time."),
        CHILL(Assets.chillIcon, "'Chill' decreases the receiver's movement speed by 33%."),
        BLEEDING(Assets.bleedIcon, "'Bleeding' inflicts damage over time."),
        POISON(Assets.poisonIcon, "'Poison' inflicts damage over time."),
        STUN(Assets.stunIcon, "'Stun' stops movement and stops the receiver from attacking.");

        Type(BufferedImage img, String description) {
            this.img = img;
            this.description = description;
        }

        private BufferedImage img;
        private String description;

        public BufferedImage getImg() {
            return img;
        }

        public String getDescription() {
            return description;
        }
    }
}
