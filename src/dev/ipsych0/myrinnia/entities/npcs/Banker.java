package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.creatures.Creature;

public abstract class Banker extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = -6734284480542153325L;

    Banker(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        attackable = false;
        isNpc = true;
    }
}
