package dev.ipsych0.mygame.abilityhud;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.abilities.AbilityType;
import dev.ipsych0.mygame.abilities.EruptionAbility;
import dev.ipsych0.mygame.abilities.FireBallAbility;
import dev.ipsych0.mygame.abilities.MendWoundsAbility;
import dev.ipsych0.mygame.abilities.NimbleFeetAbility;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.items.ItemSlot;

public class PlayerHUD implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private static final int MAX_SLOTS = 10;
	private ArrayList<AbilitySlot> slottedAbilities = new ArrayList<AbilitySlot>();
	private HPBar hpBar;
	private XPBar xpBar;
	private int x, y, width, height;
	private AbilityTooltip abilityTooltip;
	public static boolean hasBeenPressed;
	public static char pressedKey;
	
	public PlayerHUD(Handler handler) {
		this.handler = handler;
		this.width = x + ItemSlot.SLOTSIZE * MAX_SLOTS;
		this.height = y + ItemSlot.SLOTSIZE;
		this.x = handler.getWidth() / 2 - (width / 2);
		this.y = handler.getHeight() - ItemSlot.SLOTSIZE;
		
		for(int i = 0; i < MAX_SLOTS; i++) {
			slottedAbilities.add(new AbilitySlot(handler, null, x + (i * 32), y));
		}
		slottedAbilities.get(0).setAbility(new FireBallAbility(handler, CharacterStats.Fire, "Fireball", AbilityType.AutoAttack, true, 5,1,0,10, "A weak fireball spell."));
		slottedAbilities.get(1).setAbility(new EruptionAbility(handler, CharacterStats.Fire, "Eruption", AbilityType.StandardAbility, false, 10,2,0,25, "Causes an eruption that deals X AoE damage."));
		slottedAbilities.get(2).setAbility(new MendWoundsAbility(handler, CharacterStats.Water, "Mend Wounds", AbilityType.HealingAbility, false, 25,1,0,0, "Heal yourself for X amount of health and gain regeneration for Y seconds."));
		slottedAbilities.get(3).setAbility(new NimbleFeetAbility(handler, CharacterStats.Air, "Nimble Feet", AbilityType.StandardAbility, false, 20,0,0,0, "Increases movement speed by 1.0 for 5 seconds."));

		// Add HP Bar after the last abilitySlot
//		hpBar = new HPBar(handler, slottedAbilities.get(slottedAbilities.size()-1).getX() + ItemSlot.SLOTSIZE, y);
		// Add XP Bar after HP Bar
//		xpBar = new XPBar(handler, hpBar.getX() + hpBar.getWidth(), y);
		
		abilityTooltip = new AbilityTooltip(0, handler.getHeight() / 2, 160, 224);

//		this.width = x + xpBar.getX() + xpBar.getWidth();
//		this.height = y + ItemSlot.SLOTSIZE;
	}
	
	/**
	 * Handles the pressed ability slot button
	 */
	private void handleKeyEvent() {
		// Get the right index in the ability slots
		int index = 0;
		// Funky calculation. If 0 is pressed, it should be the last slot instead of first, otherwise the slot is 1-9 pressed -1 by index
		Ability selectedAbility = slottedAbilities.get(index = pressedKey == 48 ? slottedAbilities.size()-1 : (pressedKey-49)).getAbility();
		if(selectedAbility != null) {
			for(AbilitySlot as : slottedAbilities) {
				if(as.getAbility() != null) {
					if(as.getAbility().isChanneling()) {
						return;
					}
				}
			}
			if(selectedAbility.isOnCooldown()) {
				handler.sendMsg(selectedAbility.getName() + " is on cooldown.");
				return;
			}else {
				handler.getAbilityManager().getActiveAbilities().add(selectedAbility);
				selectedAbility.setCaster(handler.getPlayer());
				if(selectedAbility.isSelectable()) {
					boolean selected = !selectedAbility.isSelected() ? true : false;
					selectedAbility.setSelected(selected);
				}
			}
		}
	}
	
	private void handleClickEvent(AbilitySlot slot) {
		Ability selectedAbility = slot.getAbility();
		if(selectedAbility != null) {
			for(AbilitySlot as : slottedAbilities) {
				if(as.getAbility() != null) {
					if(as.getAbility().isChanneling()) {
						return;
					}
				}
			}
			if(selectedAbility.isOnCooldown()) {
				handler.sendMsg(selectedAbility.getName() + " is on cooldown.");
				return;
			}else {
				handler.getAbilityManager().getActiveAbilities().add(selectedAbility);
				selectedAbility.setCaster(handler.getPlayer());
				if(selectedAbility.isSelectable()) {
					boolean selected = !selectedAbility.isSelected() ? true : false;
					selectedAbility.setSelected(selected);
				}
			}
		}
	}
	
	public void tick() {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		
		if(pressedKey != '\u0000') {
			// If 0-9 key is pressed, handle the key pressed.
			handleKeyEvent();
			// Set back to default/non-pressed value
			pressedKey = '\u0000';
		}
		
		// Tick the tooltip and other bars
		for(AbilitySlot as : slottedAbilities) {
			as.tick();
			if(as.getBounds().contains(mouse)){
				abilityTooltip.tick();
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					handleClickEvent(as);
					hasBeenPressed = false;
				}
			}
		}
//		hpBar.tick();
//		xpBar.tick();
	}
	
	public void render(Graphics g) {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		
		int index = 0;
		for(AbilitySlot as : slottedAbilities) {
			// Render the slots from 1-9, with the final slot 0
			if(index++ == 9) {
				as.render(g, 0);
			}else {
				as.render(g, index);
			}
			// Render the tooltip when hovering over an ability
			if(as.getBounds().contains(mouse)){
				if(as.getAbility() != null) {
					abilityTooltip.render(g, as.getAbility());
				}
			}
		}
//		hpBar.render(g);
//		xpBar.render(g);
	}

	public ArrayList<AbilitySlot> getSlottedAbilities() {
		return slottedAbilities;
	}

	public void setSlottedAbilities(ArrayList<AbilitySlot> slottedAbilities) {
		this.slottedAbilities = slottedAbilities;
	}

	public HPBar getHpBar() {
		return hpBar;
	}

	public void setHpBar(HPBar hpBar) {
		this.hpBar = hpBar;
	}

	public XPBar getXpBar() {
		return xpBar;
	}

	public void setXpBar(XPBar xpBar) {
		this.xpBar = xpBar;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
}
