package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

@Getter
@Setter
public class Condition implements Serializable {

    private static final long serialVersionUID = -6491027693312163146L;
    private Entity receiver;
    private double duration;
    private int tickTimer;
    private boolean active;
    private int conditionDamage;
    private transient BufferedImage img;
    private double initialSpeedDecrease;
    private static final double CHILL_MOVSPD = 0.5;
    private static final double CRIPPLE_MOVSPD = 0.66;
    private Type type;

    public Condition(Type type, double durationSeconds) {
        this.type = type;
        this.img = type.getImg();
        this.duration = durationSeconds * 60d;
        this.active = true;
    }

    public Condition(Type type, double durationSeconds, int conditionDamage) {
        this.type = type;
        this.img = type.getImg();
        this.duration = durationSeconds * 60d;
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
            if (tickTimer <= duration) {
                // Tick the condition effect
                if (tickTimer == 0) {
                    apply();
                } else if (tickTimer % 60 == 0) {
                    update();
                }
                // If the condition timeLeft is 0, don't tick anymore, but let the last hitsplat disappear
            } else {
                if (tickTimer % 60 == 0) {
                    clear();
                }
            }
            tickTimer++;
        }
    }

    public void render(Graphics2D g, int x, int y) {
        if (active) {
            g.drawImage(img, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, String.valueOf(Handler.get().roundOff(((duration - tickTimer) / 60d) + 1.0)), x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }

    private void apply() {
        receiver.tickCondition(receiver, this);
        Creature r = ((Creature) receiver);
        switch (type) {
            case CHILL:
                double currMovSpd = r.getMovementSpeed();
                double newMovSpd = (r.getMovementSpeed() * CHILL_MOVSPD);
                initialSpeedDecrease = currMovSpd - newMovSpd;
                r.setMovementSpeed(newMovSpd);
                break;
            case CRIPPLED:
                double currMovSpd2 = r.getMovementSpeed();
                double newMovSpd2 = (r.getMovementSpeed() * CRIPPLE_MOVSPD);
                initialSpeedDecrease = currMovSpd2 - newMovSpd2;
                r.setMovementSpeed(newMovSpd2);
                break;
        }
    }

    private void update() {
        // After 1 second, recreate the damage splat
        receiver.tickCondition(receiver, this);
    }

    public void clear() {
        tickTimer = 0;
        this.setActive(false);

        if (type == Type.CHILL || type == Type.CRIPPLED) {
            Creature r = ((Creature) receiver);
            r.setMovementSpeed(r.getMovementSpeed() + initialSpeedDecrease);
        }
    }

    @Getter
    public enum Type {
        BURNING(Assets.burnIcon, "'Burning' inflicts damage over time."),
        CHILL(Assets.chillIcon, "'Chill' decreases the receiver's movement speed by 50%."),
        CRIPPLED(Assets.crippledIcon, "'Crippled' decreases the receiver's movement speed by 33%."),
        BLEEDING(Assets.bleedIcon, "'Bleeding' inflicts damage over time."),
        POISON(Assets.poisonIcon, "'Poison' inflicts damage over time."),
        ROOTED(Assets.rootedIcon, "'Rooted' renders the receiver immobilized."),
        BLINDED(Assets.blindedIcon, "'Blinded' makes the next attack miss."),
        STUN(Assets.stunIcon, "'Stun' immobilizes and prevents the receiver from fighting back.");

        Type(BufferedImage img, String description) {
            this.img = img;
            this.description = description;
        }

        private final BufferedImage img;
        private final String description;

    }
}
