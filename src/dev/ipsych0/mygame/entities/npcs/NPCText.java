package dev.ipsych0.mygame.entities.npcs;

import java.io.Serializable;

public class NPCText implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String line;
	
	public NPCText(String line){
		this.line = line;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
}
