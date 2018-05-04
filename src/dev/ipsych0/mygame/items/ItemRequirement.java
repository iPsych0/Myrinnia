package dev.ipsych0.mygame.items;

import java.io.Serializable;

import dev.ipsych0.mygame.character.CharacterStats;

public class ItemRequirement implements Serializable {
	
	private CharacterStats stat;
	private int level;
	
	public ItemRequirement(CharacterStats stat, int level) {
		this.stat = stat;
		this.level = level;
	}

	public CharacterStats getStat() {
		return stat;
	}

	public void setStat(CharacterStats stat) {
		this.stat = stat;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
