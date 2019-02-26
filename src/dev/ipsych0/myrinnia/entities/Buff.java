package dev.ipsych0.myrinnia.entities;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class Buff {

    protected Entity receiver;
    protected int timeLeft;
    private final int EFFECT_DURATION;
    protected boolean active;
    private boolean effectApplied;
    private int buffId = -1;
    private static Set<Integer> ids = new HashSet<>();

    public Buff(Entity receiver, int durationSeconds) {
        this.receiver = receiver;
        this.EFFECT_DURATION = durationSeconds * 60;
        this.active = true;
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
        if(buffId == -1){
            System.err.println("Forgot to set buffId for: " + this.getClass().getSimpleName());
        }
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
        ids.add(buffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buffId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(!(obj instanceof Buff)) return false;
        Buff b = (Buff)obj;
        return buffId == b.getBuffId();
    }
}
