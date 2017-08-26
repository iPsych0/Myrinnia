package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.ipsych0.mygame.Handler;

public class WorldHandler {
	private Handler handler;
	private ArrayList<World> worlds;
	private SwampLand swampLand;
	

	public WorldHandler(Handler handler, SwampLand swampLand){
		this.handler = handler;
		worlds = new ArrayList<World>();
		addWorld(swampLand);
	}
	
	public void tick(){
		Iterator<World> it = worlds.iterator();
		while(it.hasNext()){
			World w = it.next();
			w.tick();
		}
	}
	
	public void render(Graphics g){
		for(World w : worlds){
			w.render(g);
		}
	}
	
	public void addWorld(World w){
		worlds.add(w);
	}

	public ArrayList<World> getWorlds() {
		return worlds;
	}

	public void setWorlds(ArrayList<World> worlds) {
		this.worlds = worlds;
	}
	
	public SwampLand getSwampLand() {
		return swampLand;
	}

	public void setSwampLand(SwampLand swampLand) {
		this.swampLand = swampLand;
	}
}
