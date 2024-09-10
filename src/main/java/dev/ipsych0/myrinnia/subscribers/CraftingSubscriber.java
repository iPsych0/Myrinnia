package dev.ipsych0.myrinnia.subscribers;

import dev.ipsych0.myrinnia.publishers.CraftingPublisher;
import dev.ipsych0.myrinnia.publishers.Publisher;

public class CraftingSubscriber implements Subscriber {

    private final SubscriptionListener subscriptionListener;
    private final boolean oneTimeEvent;

    public CraftingSubscriber(Publisher publisher, boolean oneTimeEvent, SubscriptionListener subscriptionListener) {
        this.oneTimeEvent = oneTimeEvent;
        this.subscriptionListener = subscriptionListener;

        publisher.subscribe(this);
    }

    @Override
    public void update(Publisher publisher) {
        if (publisher instanceof CraftingPublisher) {
            CraftingPublisher craftingPublisher = (CraftingPublisher) publisher;

            // Update logic calls for Crafting events
            subscriptionListener.onMessageReceived(craftingPublisher.getCraftedItem());

            if (oneTimeEvent) {
                publisher.unsubscribe(this);
            }
        }
    }
}
