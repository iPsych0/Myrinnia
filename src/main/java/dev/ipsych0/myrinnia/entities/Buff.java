package dev.ipsych0.myrinnia.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@EqualsAndHashCode
@Getter
@Setter
public abstract class Buff {

    protected Buff incomingBuff;
    protected Entity receiver;
    protected int timeLeft;
    protected double effectDuration;
    private boolean active;
    private boolean effectApplied;
    @EqualsAndHashCode.Include private int buffId = -1;
    private static Set<Integer> ids = new HashSet<>();
    private int timesStacked = 0;
    private boolean additive;

    protected Buff(Entity receiver, double durationSeconds, boolean isAdditive) {
        this.receiver = receiver;
        this.effectDuration = durationSeconds * 60;
        this.active = true;
        this.additive = isAdditive;
    }

    public void tick() {
        if (this.isActive()) {
            // If the receiver died, stop ticking
            if (!receiver.isActive()) {
                this.setActive(false);
                clear();
                return;
            }

            if (!effectApplied) {
                applyEffect();
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

    private void applyEffect() {
        apply();
        effectApplied = true;
        timeLeft--;
        timesStacked++;
    }

    protected abstract void apply();

    protected abstract void update();

    public abstract void clear();

    public abstract void render(Graphics2D g, int x, int y);

    public abstract String getDescription();

    public abstract String toString();

    private int getBuffId() {
        if (buffId == -1) {
            log.error("Forgot to set buffId for: {}", this.getClass().getSimpleName());
        }
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
        ids.add(buffId);
    }

}
