package dev.ipsych0.myrinnia.abilityhud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

public class AbilitySlot implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4376752517769900190L;
	private Ability ability;
	private int x, y;
	private Color cooldownColor = new Color(24, 24, 24, 192);
	private Color selectedColor = new Color(64, 64, 64, 192);

	public AbilitySlot(Ability ability, int x, int y) {
		this.ability = ability;
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int slotNum) {
		g.drawImage(Assets.genericButton[0], x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
		if(ability != null) {
			ability.render(g, x, y);
			if(ability.isSelectable() && ability.isSelected()) {
				g.setColor(selectedColor);
				g.fillRoundRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);
			}
			if(ability.isOnCooldown()) {
				g.setColor(cooldownColor);
				g.fillRoundRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);
				Text.drawString(g, String.valueOf((int)ability.getRemainingCooldown()), x + 16, y, true, Color.YELLOW, Assets.font14);

			}
		}
		Text.drawString(g, String.valueOf(slotNum), x + ItemSlot.SLOTSIZE - 10, y + ItemSlot.SLOTSIZE - 4, false, Color.YELLOW, Assets.font14);
	}

	public void render(Graphics g) {
		g.drawImage(Assets.genericButton[0], x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
		if(ability != null) {
			ability.render(g, x, y);
			if(!ability.isUnlocked()){
				g.setColor(cooldownColor);
				g.fillRoundRect(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);
			}
		}
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
