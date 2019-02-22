package dev.ipsych0.myrinnia.entities;

import java.awt.*;
import java.util.List;

public abstract class Buff {

    protected Entity receiver;
    protected int timeLeft;
    private final int EFFECT_DURATION;
    protected boolean active;
    private boolean effectApplied;
    private int buffId;
    private static int counter;

    public Buff(Entity receiver, int durationSeconds) {
        this.receiver = receiver;
        this.EFFECT_DURATION = durationSeconds * 60;
        this.active = true;
        buffId = counter++;
    }

    public void tick() {
        if (this.isActive()) {
            // If the receiver died, stop ticking
            if (!receiver.isActive()) {
                this.setActive(false);
                return;
            }

            if (!effectApplied) {
                timeLeft = timeLeft + EFFECT_DURATION;
                apply();
                effectApplied = true;
                timeLeft--;
                return;
            }

            // If the timeLeft is greater than 0 at any given time
            if (timeLeft > 0) {
                // Tick the buff effect
                timeLeft--;
                update();
                // If the timeLeft expired, don't tick anymore
            } else {
                clear();
                this.setActive(false);
            }
        }
    }

    public abstract void apply();

    public abstract void update();

    public abstract void clear();

    public abstract void render(Graphics g, int x, int y);

    public boolean isEffectApplied() {
        return effectApplied;
    }

    public void setEffectApplied(boolean effectApplied) {
        this.effectApplied = effectApplied;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getBuffId() {
        return buffId;
    }
}
