package dev.ipsych0.mygame.skills;

import dev.ipsych0.mygame.Handler;

public class CombatSkill extends Skill {

	public CombatSkill(Handler handler) {
		super(handler);
	}
	
	@Override
	public void addLevel() {
		this.level++;
		handler.getCharacterUI().addBaseStatPoints();
		handler.getCharacterUI().addElementalStatPoints();
	}
	
	@Override
	public String toString() {
		return "Combat";
	}

}
