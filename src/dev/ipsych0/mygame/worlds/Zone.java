package dev.ipsych0.mygame.worlds;

import java.io.Serializable;

public enum Zone implements Serializable{
	
	Island("myrinnia.wav"), IslandUnderground("myrinnia.wav"), SwampLand("myrinnia.wav"), TestLand("myrinnia.wav");
	
	String musicFile;
	
	Zone(String musicFile){
		this.musicFile = musicFile;
	}

	public String getMusicFile() {
		return musicFile;
	}

	public void setMusicFile(String musicFile) {
		this.musicFile = musicFile;
	}

}
