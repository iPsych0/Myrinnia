package dev.ipsych0.myrinnia.abilities.effects;

import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;

import java.awt.*;

public class ConditionOnHitEvent implements OnHit {

    private Condition.Type condition;
    private Entity caster, receiver;
    private int expiryTime, activeTimer;
    private int condiDamage, condiDuration;
    private int count = 1;
    private boolean finished;
    private boolean infiniteCount = true;

    public ConditionOnHitEvent(Condition.Type condition, int condiDamage, int condiDuration, int expiryTimeSeconds) {
        this.condiDamage = condiDamage;
        this.condiDuration = condiDuration;
        this.condition = condition;
        this.expiryTime = expiryTimeSeconds * 60;
    }

    public ConditionOnHitEvent(Condition.Type condition, int condiDamage, int condiDuration, int expiryTimeSeconds, int count) {
        this.condition = condition;
        this.condiDamage = condiDamage;
        this.condiDuration = condiDuration;
        this.expiryTime = expiryTimeSeconds * 60;
        this.count = count;
        infiniteCount = false;
    }

    @Override
    public void apply(Entity caster, Entity receiver) {
        this.caster = caster;
        this.receiver = receiver;
        receiver.addCondition(caster, receiver, new Condition(condition, condiDuration, condiDamage));
        if (!infiniteCount) {
            count--;
            if (count == 0) {
                finished = true;
            }
        }
    }

    @Override
    public void tick() {
        activeTimer++;
        if (activeTimer >= expiryTime) {
            finished = true;
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void finish() {

    }

    @Override
    public void setCaster(Entity caster) {
        this.caster = caster;
    }

    @Override
    public Entity getCaster() {
        return caster;
    }

    @Override
    public void setReceiver(Entity receiver) {
        this.receiver = receiver;
    }

    @Override
    public Entity getReceiver() {
        return receiver;
    }
}
