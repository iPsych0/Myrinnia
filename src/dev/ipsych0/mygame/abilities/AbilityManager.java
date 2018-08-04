package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;

public class AbilityManager {
	
	private Handler handler;
	private CopyOnWriteArrayList<Ability> activeAbilities = new CopyOnWriteArrayList<>();
	private int preCastTimer = 0;
	private int afterCastTimer = 0;
	private Ability selectedAbility;
	private Collection<Ability> deleted = new CopyOnWriteArrayList<>();
	
	/*
	 * Abilities (maybe via file inladen)
	 */
		
	public AbilityManager(Handler handler) {
		this.handler = handler;
		
	}
	
	public void tick() {
		Iterator<Ability> it = activeAbilities.iterator();
		while(it.hasNext()){
			Ability a = it.next();
			if(a.isActivated()) {
				a.tick();
			}else {
				deleted.add(a);
			}
		}
		
		// Clear the non-active abilities
		if(deleted.size() > 0) {
			activeAbilities.removeAll(deleted);
			deleted.clear();
		}
	}
	
	public void render(Graphics g) {

	}

	public CopyOnWriteArrayList<Ability> getActiveAbilities() {
		return activeAbilities;
	}

	public void setActiveAbilities(CopyOnWriteArrayList<Ability> activeAbilities) {
		this.activeAbilities = activeAbilities;
	}


}
