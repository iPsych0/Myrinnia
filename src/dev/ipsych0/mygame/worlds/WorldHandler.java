package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.skills.SkillsList;

public class WorldHandler implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private ArrayList<World> worlds;
	private HashMap<Zone, World> worldsMap;

	public WorldHandler(Handler handler, Island island){
		this.handler = handler;
		worlds = new ArrayList<World>();
		worldsMap = new HashMap<Zone, World>();
		addWorld(island); // Starting world
		
		initWorlds();
	}
	
	private void initWorlds() {
		// Add new worlds here
		addWorld(new TestLand(handler, "res/worlds/testmap2.tmx"));
		addWorld(new SwampLand(handler, "res/worlds/testmap.tmx"));
		addWorld(new IslandUnderground(handler, "res/worlds/island_indoors.tmx"));
		
		try {
			Collections.sort(worlds, (o1, o2) ->
				o1.getClass().getSimpleName().toLowerCase()
				.compareTo(o2.getClass().getSimpleName().toLowerCase()));
			List<Zone> zoneEnum = Arrays.asList(Zone.values());
			Collections.sort(zoneEnum, (o1, o2) -> o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase()));
			
			for(int i = 0; i < worlds.size(); i++) {
				worldsMap.put(zoneEnum.get(i), worlds.get(i));
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("ERROR: World enum / World class not found!");
		}
		
	}
	
	public void tick(){
		Iterator<World> it = worlds.iterator();
		while(it.hasNext()){
			World w = it.next();
			w.tick();
		}
	}
	
	public void render(Graphics g){
		Iterator<World> it = worlds.iterator();
		while(it.hasNext()){
			World w = it.next();
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

	public HashMap<Zone, World> getWorldsMap() {
		return worldsMap;
	}

	public void setWorldsMap(HashMap<Zone, World> worldsMap) {
		this.worldsMap = worldsMap;
	}
}
