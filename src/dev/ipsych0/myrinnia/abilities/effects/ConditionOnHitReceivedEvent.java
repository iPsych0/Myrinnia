package dev.ipsych0.myrinnia.abilities.effects;

import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;

import java.awt.*;

public class ConditionOnHitReceivedEvent implements OnHitReceived {

    private Condition.Type condition;
    private Entity caster, receiver;
    private int expiryTicks, activeTimer;
    private int condiDamage, condiDuration;
    private int count = 1;
    private boolean finished;
    private boolean infiniteCount = true;

    public ConditionOnHitReceivedEvent(Condition.Type condition, int condiDamage, int condiDuration, int expiryTicks) {
        this.condiDamage = condiDamage;
        this.condiDuration = condiDuration;
        this.condition = condition;
        this.expiryTicks = expiryTicks;
    }

    public ConditionOnHitReceivedEvent(Condition.Type condition, int condiDamage, int condiDuration, int expiryTicks, int count) {
        this.condition = condition;
        this.condiDamage = condiDamage;
        this.condiDuration = condiDuration;
        this.expiryTicks = expiryTicks;
        this.count = count;
        infiniteCount = false;
    }

    @Override
    public void apply(Entity caster, Entity receiver) {
        this.caster = caster;
        this.receiver = receiver;
        receiver.addCondition(caster, new Condition(condition, condiDuration, condiDamage));
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
        if (activeTimer >= expiryTicks) {
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
