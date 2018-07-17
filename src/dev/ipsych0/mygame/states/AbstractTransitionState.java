package dev.ipsych0.mygame.states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;
import dev.ipsych0.mygame.worlds.Zone;

public abstract class AbstractTransitionState extends State{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected float alpha = 1.0f;
	
	public AbstractTransitionState(Handler handler){
		super(handler);
	}

	@Override
	public abstract void tick();

	@Override
	public abstract void render(Graphics g);
}
