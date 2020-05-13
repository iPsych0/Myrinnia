package dev.ipsych0.myrinnia.publishers;

import dev.ipsych0.myrinnia.subscribers.Subscriber;
import dev.ipsych0.myrinnia.skills.Skill;

import java.util.HashSet;
import java.util.Set;

public class SkillPublisher implements Publisher {

    private static SkillPublisher instance;
    private final Set<Subscriber> subscribers;
    private Skill skill;

    private SkillPublisher() {
        subscribers = new HashSet<>();
    }

    public static SkillPublisher get() {
        if (instance == null) {
            instance = new SkillPublisher();
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

    public void publish(Skill skill) {
        this.skill = skill;
        notifySubscribers();
    }

    public Skill getSkill() {
        return skill;
    }
}
