package dev.ipsych0.mygame.entities.npcs;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.ipsych0.mygame.Handler;

public class NPCManager {
	
	// NPCs

	
	// Class stuff
	private Handler handler;
	private ArrayList<NPC> npcs;
	private Comparator<NPC> renderSorter = new Comparator<NPC>(){
		@Override
		public int compare(NPC a, NPC b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight()){
				return -1;
			}else{
				return 1;
			}
		}
	};
	
	public NPCManager(Handler handler){
		this.handler = handler;
		npcs = new ArrayList<NPC>();
	}
	
	public void tick(){
		Iterator<NPC> it = npcs.iterator();
		while(it.hasNext()){
			NPC npc = it.next();
			npc.tick();
		}
		npcs.sort(renderSorter);
	}
	
	public void render(Graphics g){
		for(NPC npc : npcs){
			npc.render(g);
		}
	}
	
	public void addNPC(NPC npc){
		npcs.add(npc);
	}

	// Getters & Setters
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<NPC> getNPCs() {
		return npcs;
	}

	public void setNPCs(ArrayList<NPC> npcs) {
		this.npcs = npcs;
	}

}
