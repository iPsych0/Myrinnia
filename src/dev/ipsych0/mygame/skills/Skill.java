package dev.ipsych0.mygame.skills;

import java.io.Serializable;

public abstract class Skill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int experience, level;
	protected int nextLevelXp = 100;
	
	public Skill() {
		experience = 0;
		level = 1;
	}

	public int getExperience() {
		return experience;
	}

	public void addExperience(int experience) {
		this.experience += experience;
		checkNextLevel();
	}

	public int getLevel() {
		return level;
	}

	public void addLevel() {
		this.level++;
	}
	
	private void checkNextLevel() {
		if(this.experience >= nextLevelXp) {
			experience -= nextLevelXp;
			addLevel();
			nextLevelXp = (int)(nextLevelXp * 1.1);
			checkNextLevel();
		}
	}

	public int getNextLevelXp() {
		return nextLevelXp;
	}

}
