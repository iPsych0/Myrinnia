package dev.ipsych0.myrinnia.puzzles;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class Puzzle implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1393761869484926871L;
	protected boolean completed;
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	

}
