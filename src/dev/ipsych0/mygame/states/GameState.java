package dev.ipsych0.mygame.states;

import java.awt.Font;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.worlds.World;

public class GameState extends State{

	private World world;
	public static Font myFont;
	public static Font chatFont = new Font("SansSerif", Font.BOLD, 14);
	
	public GameState(Handler handler){
		super(handler);
		world = new World(handler, "res/worlds/MyrinniaTown.txt");
		handler.setWorld(world);
		myFont = new Font("SansSerif", Font.PLAIN, 12);
	}
	
	@Override
	public void tick() {
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		g.setFont(myFont);
	}

}
