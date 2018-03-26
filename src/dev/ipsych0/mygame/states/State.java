package dev.ipsych0.mygame.states;

import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;

public abstract class State implements Serializable{
	
	private static State currentState = null;
	
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	//CLASS
	
	protected Handler handler;
	
	public State(Handler handler){
		this.handler = handler;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

}
