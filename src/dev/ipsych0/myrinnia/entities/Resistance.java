package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Resistance implements Serializable {

    private static final long serialVersionUID = 3317119477719007572L;
    private Condition.Type type;
    private int expiryTime;
    private int timer;
    private boolean active;
    private transient BufferedImage img;
    private double effectiveness;
    private String description;

    public Resistance(Condition.Type type, int expiryTime, double effectiveness) {
        this.type = type;
        this.expiryTime = expiryTime;
        this.effectiveness = effectiveness;
        this.img = type.getImg();
        this.description = getDescription(type);

        active = true;
    }

    // Set the resistance description
    private String getDescription(Condition.Type type) {
        String desc = "Reduces the %s of %s by %.1f";
        String name = type.toString().substring(0, 1) + type.toString().substring(1).toLowerCase();
        String category = "duration";

        switch (type) {
            case BURNING:
            case BLEEDING:
            case POISON:
                category = "damage";
                desc = String.format(desc, category, name, effectiveness * 100d) + "%.";
                return desc;
            default:
                desc = String.format(desc, category, name, effectiveness * 100d) + "%.";
                return desc;
        }
    }

    public void tick() {
        timer++;
        if (timer >= expiryTime) {
            active = false;
        }
    }

    public void render(Graphics2D g, int x, int y) {
        if (active) {
            g.drawImage(img, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            g.drawImage(Assets.resistanceIcon, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);

            int timeLeft = (expiryTime - timer) / 60 + 1;
            String text = String.valueOf(timeLeft);

            // Draw minutes left if time left is greater than 60 seconds
            if (timeLeft >= 60) {
                text = timeLeft / 60 + "m";
            }

            Text.drawString(g, text, x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }

    public Condition.Type getType() {
        return type;
    }

    public int getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(int expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }

    public double getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
        this.description = getDescription(type);
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
