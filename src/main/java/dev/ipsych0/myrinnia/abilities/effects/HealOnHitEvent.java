package dev.ipsych0.myrinnia.abilities.effects;

import dev.ipsych0.myrinnia.entities.Entity;

import java.awt.*;

public class HealOnHitEvent implements OnHit {

    private boolean finished;
    private Entity caster, receiver;
    private OnHit onHit;

    public HealOnHitEvent(OnHit onHit) {
        this.onHit = onHit;
    }

    @Override
    public void apply(Entity caster, Entity receiver) {
        this.caster = caster;
        this.receiver = receiver;
    }

    @Override
    public void tick() {

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
