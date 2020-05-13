package dev.ipsych0.myrinnia.subscribers;

import dev.ipsych0.myrinnia.publishers.Publisher;
import dev.ipsych0.myrinnia.publishers.SkillPublisher;

public class SkillSubscriber implements Subscriber {

    private final SubscriptionListener subscriptionListener;
    private boolean oneTimeEvent;

    public SkillSubscriber(Publisher publisher, boolean oneTimeEvent, SubscriptionListener subscriptionListener) {
        this.oneTimeEvent = oneTimeEvent;
        this.subscriptionListener = subscriptionListener;

        publisher.subscribe(this);
    }

    @Override
    public void update(Publisher publisher) {
        if (publisher instanceof SkillPublisher) {
            SkillPublisher skillPublisher = (SkillPublisher) publisher;

            // Update logic of leveling up a skill
            subscriptionListener.onMessageReceived(skillPublisher.getSkill());

            if (oneTimeEvent) {
                publisher.unsubscribe(this);
            }
        }
    }
}
