package dev.ipsych0.myrinnia.subscribers;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.publishers.Publisher;
import dev.ipsych0.myrinnia.publishers.SkillPublisher;

public class SkillSubscriber implements Subscriber {

    private final SubscriptionListener subscriptionListener;

    public SkillSubscriber(Publisher publisher, SubscriptionListener subscriptionListener) {
        publisher.subscribe(this);
        this.subscriptionListener = subscriptionListener;
    }

    @Override
    public void update(Publisher publisher) {
        if (publisher instanceof SkillPublisher) {
            SkillPublisher skillPublisher = (SkillPublisher) publisher;

            // Update logic of leveling up a skill
            subscriptionListener.onMessageReceived(skillPublisher.getSkill());
        }
    }
}
