package dev.ipsych0.myrinnia.subscribers;

import dev.ipsych0.myrinnia.publishers.Publisher;
import dev.ipsych0.myrinnia.publishers.WorldPublisher;

public class WorldSubscriber implements Subscriber {

    private final SubscriptionListener subscriptionListener;
    private final boolean oneTimeEvent;

    public WorldSubscriber(Publisher publisher, boolean oneTimeEvent, SubscriptionListener subscriptionListener) {
        this.oneTimeEvent = oneTimeEvent;
        this.subscriptionListener = subscriptionListener;

        publisher.subscribe(this);
    }

    @Override
    public void update(Publisher publisher) {
        if (publisher instanceof WorldPublisher) {
            WorldPublisher worldPublisher = (WorldPublisher) publisher;

            // Update logic of leveling up a skill
            subscriptionListener.onMessageReceived(worldPublisher.getGoToWorld());

            if (oneTimeEvent) {
                publisher.unsubscribe(this);
            }
        }
    }
}