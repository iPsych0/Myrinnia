package dev.ipsych0.mygame.skills;

public abstract class Skill {
	
	protected int experience, level;
	protected double nextLevelXp = 50;

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
			experience = (int)(experience % nextLevelXp);
			addLevel();
			nextLevelXp = (int)(nextLevelXp * 1.1);
		}
	}

}
