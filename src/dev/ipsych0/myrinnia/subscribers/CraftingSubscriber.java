package dev.ipsych0.myrinnia.subscribers;

import dev.ipsych0.myrinnia.publishers.CraftingPublisher;
import dev.ipsych0.myrinnia.publishers.Publisher;

public class CraftingSubscriber implements Subscriber {

    private final SubscriptionListener subscriptionListener;

    public CraftingSubscriber(Publisher publisher, SubscriptionListener subscriptionListener) {
        publisher.subscribe(this);
        this.subscriptionListener = subscriptionListener;
    }

    @Override
    public void update(Publisher publisher) {
        if (publisher instanceof CraftingPublisher) {
            CraftingPublisher pub = (CraftingPublisher) publisher;

            // Update logic calls for Crafting events
            subscriptionListener.onMessageReceived(pub.getCraftedItem());
        }
    }
}
