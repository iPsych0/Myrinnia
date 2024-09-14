package dev.ipsych0.myrinnia.abilities.data;

import dev.ipsych0.myrinnia.entities.Entity;

import java.io.Serializable;

@FunctionalInterface
public interface OnImpact extends Serializable {

    void impact(Entity receiver);
}
