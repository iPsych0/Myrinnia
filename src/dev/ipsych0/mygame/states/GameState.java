package dev.ipsych0.mygame.states;

import java.awt.Font;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.worlds.World;

public class GameState extends State{

	private World world;
	public static Font myFont;
	
	public GameState(Handler handler){
		super(handler);
		world = new World(handler, "res/worlds/MyrinniaTown.txt");
		handler.setWorld(world);
		myFont = new Font("SansSerif", Font.BOLD, 64);
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
