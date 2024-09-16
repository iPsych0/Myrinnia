package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.creatures.Creature;

import java.util.Map;

public abstract class Banker extends Creature {


    private static final long serialVersionUID = -6734284480542153325L;

    Banker(float x, float y, int width, int height, Map<String, String> props) {
        super(x, y, width, height, props);
        attackable = false;
        isNpc = true;
    }
}
