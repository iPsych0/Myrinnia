package dev.ipsych0.mygame.abilities;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilityhud.PlayerHUD;

public class AbilityManager implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1274154274799386875L;
	private CopyOnWriteArrayList<Ability> activeAbilities = new CopyOnWriteArrayList<>();
	private Collection<Ability> deleted = new CopyOnWriteArrayList<>();
	private Color castBarColor = new Color(240,160,5,224);
	private PlayerHUD playerHUD;
	
	/*
	 * Abilities (maybe via file inladen)
	 */
		
	public AbilityManager() {
		this.playerHUD = new PlayerHUD();
		
	}
	
	public void tick() {
		playerHUD.tick();
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
		playerHUD.render(g);
		Iterator<Ability> it = activeAbilities.iterator();
		while(it.hasNext()){
			Ability a = it.next();
			if(a.isActivated()) {
				if(!a.isCasting() && a.getCastingTime() > 0 && a.getCastingTimeTimer() <= a.getCastingTime()*60 && a.getCastingTimeTimer() > 0) {
					float timer = a.getCastingTimeTimer();
					float castTime =  a.getCastingTime() * 60;
					float timeLeft = timer/castTime;
					g.setColor(castBarColor);
					g.fillRect((int)(a.getCaster().getX() - Handler.get().getGameCamera().getxOffset() - 4), (int)(a.getCaster().getY() + a.getCaster().getHeight()-4 - Handler.get().getGameCamera().getyOffset()),
							(int)(timeLeft * (a.getCaster().getWidth() + 4)), 8);
					g.setColor(Color.BLACK);
					g.drawRect((int)(a.getCaster().getX() - Handler.get().getGameCamera().getxOffset() - 4), (int)(a.getCaster().getY() + a.getCaster().getHeight()-4 - Handler.get().getGameCamera().getyOffset()),
							a.getCaster().getWidth() + 4, 8);
				}
			}
		}
	}

	public CopyOnWriteArrayList<Ability> getActiveAbilities() {
		return activeAbilities;
	}

	public void setActiveAbilities(CopyOnWriteArrayList<Ability> activeAbilities) {
		this.activeAbilities = activeAbilities;
	}

	public PlayerHUD getPlayerHUD() {
		return playerHUD;
	}

	public void setPlayerHUD(PlayerHUD playerHUD) {
		this.playerHUD = playerHUD;
	}


}
