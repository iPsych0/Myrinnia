package dev.ipsych0.mygame.entities;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;

public class EntityManager {
	
	private Handler handler;
	private Player player;
	private CopyOnWriteArrayList<Entity> entities;
	private Entity shoppingNpc;
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight()){
				return -1;
			}else{
				return 1;
			}
		}
	};
	
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		entities = new CopyOnWriteArrayList<Entity>();
		addEntity(player);
	}
	
	public void tick(){
		Iterator<Entity> it = entities.iterator();
		Collection<Entity> deleted = new CopyOnWriteArrayList<Entity>();
		while(it.hasNext()){
			Entity e = it.next();
			if(!e.isActive()){
				deleted.add(e);
			}
			e.tick();
		}
		entities.removeAll(deleted);
		entities.sort(renderSorter);
	}
	
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
			if(e.shopping) {
				shoppingNpc = e;
			}
			if(e.isDamaged() && e.getDamageDealer() != null) {
				e.drawDamage(e.getDamageDealer(), g);
			}
		}
		player.postRender(g);
		
		if(shoppingNpc != null)
			shoppingNpc.postRender(g);
	}
	
	public void addEntity(Entity e){
		entities.add(e);
//		ListIterator<Entity> it = entities.listIterator();
//		it.add(e);
//		it.previous();
	}

	// Getters & Setters
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public CopyOnWriteArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(CopyOnWriteArrayList<Entity> entities) {
		this.entities = entities;
	}

}
