package dev.ipsych0.mygame.abilities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilityhud.PlayerHUD;

public class AbilityManager {
	
	private Handler handler;
	private CopyOnWriteArrayList<Ability> activeAbilities = new CopyOnWriteArrayList<>();
	private Collection<Ability> deleted = new CopyOnWriteArrayList<>();
	private Color castBarColor = new Color(180,135,5,128);
	private PlayerHUD playerHUD;
	
	/*
	 * Abilities (maybe via file inladen)
	 */
		
	public AbilityManager(Handler handler) {
		this.handler = handler;
		this.playerHUD = new PlayerHUD(handler, 0, handler.getHeight() - 32);
		
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
				if(!a.isCasting() && a.getCastingTime() > 0 && a.getCastingTimeTimer() <= a.getCastingTime()*60) {
					float timer = a.getCastingTimeTimer();
					float castTime =  a.getCastingTime() * 60;
					float timeLeft = timer/castTime;
					g.setColor(castBarColor);
					g.fillRect((int)(a.getCaster().getX() - handler.getGameCamera().getxOffset() - 4), (int)(a.getCaster().getY() + a.getCaster().getHeight()-4 - handler.getGameCamera().getyOffset()),
							(int)(timeLeft * (a.getCaster().getWidth() + 4)), 8);
					g.setColor(Color.BLACK);
					g.drawRect((int)(a.getCaster().getX() - handler.getGameCamera().getxOffset() - 4), (int)(a.getCaster().getY() + a.getCaster().getHeight()-4 - handler.getGameCamera().getyOffset()),
							(int)(timeLeft * (a.getCaster().getWidth() + 4)), 8);
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
