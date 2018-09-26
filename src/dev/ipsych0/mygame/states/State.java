package dev.ipsych0.mygame.states;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class State implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5090297819542167284L;
	private static State currentState = null;
	public static boolean hasBeenPressed = false;
	
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	//CLASS
		
	public State(){
		
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

}
