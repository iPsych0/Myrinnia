package dev.ipsych0.myrinnia.subscribers;

import java.io.Serializable;

public interface SubscriptionListener extends Serializable {
    void onMessageReceived(Object data);
}
