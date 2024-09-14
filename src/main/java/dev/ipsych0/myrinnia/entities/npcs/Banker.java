package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.Entity;

public abstract class Banker extends Entity {


    private static final long serialVersionUID = -6734284480542153325L;

    Banker() {
        
        attackable = false;
        isNpc = true;
    }
}
