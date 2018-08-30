package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;

public class CombatSkill extends Skill {

	public CombatSkill() {
		super();
	}
	
	@Override
	public void addLevel() {
		this.level++;
		Handler.get().getCharacterUI().addBaseStatPoints();
		Handler.get().getCharacterUI().addElementalStatPoints();
	}
	
	@Override
	public String toString() {
		return "Combat";
	}

}
