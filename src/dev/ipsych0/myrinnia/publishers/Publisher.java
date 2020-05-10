package dev.ipsych0.myrinnia.publishers;

import dev.ipsych0.myrinnia.subscribers.Subscriber;

import java.io.Serializable;

public interface Publisher extends Serializable {
    void subscribe(Subscriber sub);

    void unsubscribe(Subscriber sub);

    void notifySubscribers();
}
