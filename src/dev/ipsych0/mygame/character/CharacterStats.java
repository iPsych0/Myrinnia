package dev.ipsych0.mygame.character;

public enum CharacterStats {
	
	Melee, Ranged, Magic, Fire, Air, Earth, Water;
	
	private int level;
	
	CharacterStats(){
		level = 0;
	}

	public int getLevel() {
		return level;
	}

	public void addLevel() {
		this.level +=1 ;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	

}