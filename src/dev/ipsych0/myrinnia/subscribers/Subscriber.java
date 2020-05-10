package dev.ipsych0.myrinnia.subscribers;

import dev.ipsych0.myrinnia.publishers.Publisher;

import java.io.Serializable;

public interface Subscriber extends Serializable {
    void update(Publisher publisher);
}
