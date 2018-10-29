package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.creatures.Creature;

public abstract class Banker extends Creature{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6734284480542153325L;

	public Banker(float x, float y) {
		super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		attackable = false;
		isNpc = true;
	}
}
