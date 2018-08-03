package dev.ipsych0.mygame.abilityhud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.utils.Text;

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
	
	public void render(Graphics g, int slotNum) {
		g.drawImage(Assets.genericButton[0], x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
		if(ability != null)
			ability.render(g, x, y);
		Text.drawString(g, String.valueOf(slotNum), x + ItemSlot.SLOTSIZE - 10, y + ItemSlot.SLOTSIZE - 4, false, Color.YELLOW, Assets.font14);
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
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
	}
	

}
