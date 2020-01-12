package dev.ipsych0.myrinnia.abilities.effects;

import dev.ipsych0.myrinnia.entities.Entity;

import java.awt.*;

public interface EffectEvent {
    void apply(Entity caster, Entity receiver);

    void tick();

    void render(Graphics2D g);

    boolean isFinished();

    void finish();

    void setCaster(Entity caster);

    Entity getCaster();

    void setReceiver(Entity receiver);

    Entity getReceiver();
}
