package dev.ipsych0.myrinnia.publishers;

import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.subscribers.Subscriber;
import dev.ipsych0.myrinnia.worlds.World;

import java.util.HashSet;
import java.util.Set;

public class WorldPublisher implements Publisher {

    private static WorldPublisher instance;
    private final Set<Subscriber> subscribers;
    private World goToWorld;

    private WorldPublisher() {
        subscribers = new HashSet<>();
    }

    public static WorldPublisher get() {
        if (instance == null) {
            instance = new WorldPublisher();
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

    public World getGoToWorld() {
        return goToWorld;
    }

    public void publish(World goToWorld) {
        this.goToWorld = goToWorld;
        notifySubscribers();
    }
}
