package dev.ipsych0.myrinnia.items;

import java.io.Serializable;

import dev.ipsych0.myrinnia.character.CharacterStats;

public class ItemRequirement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3896881180268598158L;
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
