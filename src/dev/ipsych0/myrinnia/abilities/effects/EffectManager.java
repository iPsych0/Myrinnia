package dev.ipsych0.myrinnia.abilities.effects;

import dev.ipsych0.myrinnia.entities.Entity;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EffectManager {

    private Set<EffectEvent> events;
    private static EffectManager instance;

    private EffectManager() {
        events = new HashSet<>();
    }

    public static EffectManager get() {
        if (instance == null) {
            instance = new EffectManager();
        }
        return instance;
    }

    public void addEvent(Entity caster, EffectEvent event) {
        events.remove(event);
        events.add(event);
        event.setCaster(caster);
    }

    public void applyOnHitEffect(Entity dealer, Entity receiver) {
        for (EffectEvent event : events) {
            if (event instanceof OnHit && dealer.equals(event.getCaster())) {
                event.apply(dealer, receiver);
            }
        }
    }

    public void applyOnHitReceivedEffect(Entity dealer, Entity receiver) {
        for (EffectEvent event : events) {
            if (event instanceof OnHitReceived && receiver.equals(event.getCaster())) {
                event.apply(receiver, dealer);
            }
        }
    }

    public void tick() {
        Iterator<EffectEvent> it = events.iterator();
        while (it.hasNext()) {
            EffectEvent event = it.next();
            if (event.isFinished()) {
                event.finish();
                it.remove();
            } else {
                event.tick();
            }
        }
    }

    public void render(Graphics2D g) {
        Iterator<EffectEvent> it = events.iterator();
        while (it.hasNext()) {
            EffectEvent event = it.next();
            if (event.isFinished()) {
                event.finish();
                it.remove();
            } else {
                event.render(g);
            }
        }
    }
}
