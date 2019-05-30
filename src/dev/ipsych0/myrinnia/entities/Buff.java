package dev.ipsych0.myrinnia.entities;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Buff {

    protected Entity receiver;
    protected int timeLeft;
    private final int effectDuration;
    private boolean active;
    private boolean effectApplied;
    private int buffId = -1;
    private static Set<Integer> ids = new HashSet<>();
    private int timesStacked = 0;

    protected Buff(Entity receiver, int durationSeconds) {
        this.receiver = receiver;
        this.effectDuration = durationSeconds * 60;
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
                timeLeft = timeLeft + effectDuration;
                apply();
                effectApplied = true;
                timeLeft--;
                timesStacked++;
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

    protected abstract void apply();

    protected abstract void update();

    protected abstract void clear();

    public abstract void render(Graphics2D g, int x, int y);

    public abstract String getDescription();

    public abstract String toString();

    public int getTimesStacked() {
        return timesStacked;
    }

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

    private void setActive(boolean active) {
        this.active = active;
    }

    private int getBuffId() {
        if(buffId == -1){
            System.err.println("Forgot to set buffId for: " + this.getClass().getSimpleName());
        }
        return buffId;
    }

    public int getEffectDuration() {
        return effectDuration;
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
