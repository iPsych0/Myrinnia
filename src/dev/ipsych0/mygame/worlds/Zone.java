package dev.ipsych0.mygame.worlds;

import java.io.Serializable;

public enum Zone {
	
	Island("Island","myrinnia.wav"), IslandUnderground("Island Underground","omniscient.wav"), SwampLand("Swamp Land","ancient.wav"), TestLand("Test Land","myrinnia.wav");
	
	private String musicFile;
	private String name;
	
	Zone(String name, String musicFile){
		this.name = name;
		this.musicFile = musicFile;
	}

	public String getMusicFile() {
		return musicFile;
	}

	public void setMusicFile(String musicFile) {
		this.musicFile = musicFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
