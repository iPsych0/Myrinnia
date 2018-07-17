package dev.ipsych0.mygame.states;

import java.awt.Font;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.worlds.Zone;

public class GameState extends State{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Font myFont;
	public static Font chatFont = new Font("SansSerif", Font.BOLD, 16);
	
	public GameState(Handler handler){
		super(handler);
		// Setup new game world
		handler.setWorld(handler.getWorldHandler().getWorldsMap().get(Zone.Island));
		myFont = new Font("SansSerif", Font.BOLD, 12);
		
	}
	
	@Override
	public void tick() {
		handler.getWorldHandler().tick();
	}

	@Override
	public void render(Graphics g) {
		handler.getWorldHandler().render(g);
	}
}
