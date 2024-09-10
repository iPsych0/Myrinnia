package dev.ipsych0.myrinnia.publishers;

import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.subscribers.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class CraftingPublisher implements Publisher {

    private static CraftingPublisher instance;
    private final Set<Subscriber> subscribers;
    private ItemStack craftedItem;

    private CraftingPublisher() {
        subscribers = new HashSet<>();
    }

    public static CraftingPublisher get() {
        if (instance == null) {
            instance = new CraftingPublisher();
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

    public ItemStack getCraftedItem() {
        return craftedItem;
    }

    public void publish(ItemStack craftedItem) {
        this.craftedItem = craftedItem;
        notifySubscribers();
    }
}
