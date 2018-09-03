package dev.ipsych0.mygame.states;

import java.awt.Graphics;

public abstract class AbstractTransitionState extends State{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected float alpha = 1.0f;
	
	public AbstractTransitionState(){
		super();
	}

	@Override
	public abstract void tick();

	@Override
	public abstract void render(Graphics g);
}
