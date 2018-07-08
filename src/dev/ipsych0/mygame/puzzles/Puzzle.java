package dev.ipsych0.mygame.puzzles;

import java.awt.Graphics;

public abstract class Puzzle {
	
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
