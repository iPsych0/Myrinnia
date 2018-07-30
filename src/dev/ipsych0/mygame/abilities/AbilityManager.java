package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;

public class AbilityManager {
	
	private Handler handler;
	private CopyOnWriteArrayList<Ability> abilities = new CopyOnWriteArrayList<>();
	private int preCastTimer = 0;
	private int afterCastTimer = 0;
	private Ability selectedAbility;
	
	/*
	 * Abilities (maybe via file inladen)
	 */
		
	public AbilityManager(Handler handler) {
		this.handler = handler;
		
	}
	
	public void tick() {

	}
	
	public void render(Graphics g) {

	}
	
	private void preCast() {
		// Timer logic for pre-cast (channel/casting time)
	}
	
	private void postCast() {
		// Timer logic for post-cast (afterCast timer)
	}

	public CopyOnWriteArrayList<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(CopyOnWriteArrayList<Ability> abilities) {
		this.abilities = abilities;
	}

}
