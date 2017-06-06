package dev.ipsych0.mygame.states;

import java.awt.Font;
import java.awt.Graphics;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.worlds.SwampLand;
import dev.ipsych0.mygame.worlds.TestLand;
import dev.ipsych0.mygame.worlds.World;
import dev.ipsych0.mygame.worlds.WorldHandler;

public class GameState extends State{

	private SwampLand swampLand;
	public static Font myFont;
	public static Font chatFont = new Font("SansSerif", Font.BOLD, 14);
	
	public GameState(Handler handler){
		super(handler);
		// Setup new game world
		handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
		myFont = new Font("SansSerif", Font.BOLD, 12);
		
		// Initialize other worlds <-- COMMENT IF BROKEN GAME
		
	}
	
	@Override
	public void tick() {
		handler.getWorldHandler().tick();
	}

	@Override
	public void render(Graphics g) {
		handler.getWorldHandler().render(g);
		g.setFont(myFont);
	}
}
