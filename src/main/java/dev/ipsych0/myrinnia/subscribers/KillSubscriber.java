package dev.ipsych0.myrinnia.subscribers;

import dev.ipsych0.myrinnia.publishers.KillPublisher;
import dev.ipsych0.myrinnia.publishers.Publisher;

public class KillSubscriber implements Subscriber {

    private final SubscriptionListener subscriptionListener;
    private final boolean oneTimeEvent;

    public KillSubscriber(Publisher publisher, boolean oneTimeEvent, SubscriptionListener subscriptionListener) {
        this.oneTimeEvent = oneTimeEvent;
        this.subscriptionListener = subscriptionListener;

        publisher.subscribe(this);
    }

    @Override
    public void update(Publisher publisher) {
        if (publisher instanceof KillPublisher) {
            KillPublisher killPublisher = (KillPublisher) publisher;

            // Update logic of leveling up a skill
            subscriptionListener.onMessageReceived(killPublisher.getKilledEntity());

            if (oneTimeEvent) {
                publisher.unsubscribe(this);
            }
        }
    }
}
