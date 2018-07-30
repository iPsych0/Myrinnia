package dev.ipsych0.mygame.abilityhud;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.items.ItemSlot;

public class AbilitySlot {
	
	private Handler handler;
	private Ability ability;
	private int x, y;

	public AbilitySlot(Handler handler, Ability ability, int x, int y) {
		this.handler = handler;
		this.ability = ability;
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
		if(ability != null)
			ability.render(g, x, y);
	}
	
	public Ability getAbility() {
		return ability;
	}
	
	public void setAbility(Ability ability) {
		this.ability = ability;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	
	

}
