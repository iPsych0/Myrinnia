package dev.ipsych0.myrinnia.publishers;

import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.subscribers.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class KillPublisher implements Publisher {

    private static KillPublisher instance;
    private final Set<Subscriber> subscribers;
    private Entity killedEntity;

    private KillPublisher() {
        subscribers = new HashSet<>();
    }

    public static KillPublisher get() {
        if (instance == null) {
            instance = new KillPublisher();
        }
        return instance;
    }

    @Override
    public void subscribe(Subscriber sub) {
        subscribers.add(sub);
    }

    @Override
    public void unsubscribe(Subscriber sub) {
        subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(this);
        }
    }

    public Entity getKilledEntity() {
        return killedEntity;
    }

    public void publish(Entity killedEntity) {
        this.killedEntity = killedEntity;
        notifySubscribers();
    }
}
