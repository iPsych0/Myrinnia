package dev.ipsych0.myrinnia.abilities.data;

import dev.ipsych0.myrinnia.entities.creatures.Creature;

import java.io.Serializable;

public interface OnImpact extends Serializable {

    void impact(Creature receiver);
}
