package dev.ipsych0.myrinnia.abilityhud;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import dev.ipsych0.abilitymaker.JSONAbility;
import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.AbilityType;
import dev.ipsych0.myrinnia.abilities.EruptionAbility;
import dev.ipsych0.myrinnia.abilities.FireBallAbility;
import dev.ipsych0.myrinnia.abilities.MendWoundsAbility;
import dev.ipsych0.myrinnia.abilities.NimbleFeetAbility;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.items.ItemSlot;
import dev.ipsych0.myrinnia.utils.Utils;

public class PlayerHUD implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2357471540127327333L;
	private static final int MAX_SLOTS = 10;
	private ArrayList<AbilitySlot> slottedAbilities = new ArrayList<>();
	private HPBar hpBar;
	private XPBar xpBar;
	private static int x, y, width, height;
	private AbilityTooltip abilityTooltip;
	public static boolean hasBeenPressed;
	public static boolean hasBeenTyped;
	public static char pressedKey;

	public PlayerHUD() {
		width = x + ItemSlot.SLOTSIZE * MAX_SLOTS;
		height = y + ItemSlot.SLOTSIZE;
		x = Handler.get().getWidth() / 2 - (width / 2);
		y = Handler.get().getHeight() - ItemSlot.SLOTSIZE - 8;
		
		for(int i = 0; i < MAX_SLOTS; i++) {
			slottedAbilities.add(new AbilitySlot(null, x + (i * 32), y));
		}

        for(int i = 0; i < Utils.abilityJsonDirectory.listFiles().length; i++){
            slottedAbilities.get(i).setAbility(Utils.loadAbility(Utils.abilityJsonDirectory.listFiles()[i].getName()));
        }

		// Add HP Bar after the last abilitySlot
//		hpBar = new HPBar(Handler.get(), slottedAbilities.get(slottedAbilities.size()-1).getX() + ItemSlot.SLOTSIZE, y);
		// Add XP Bar after HP Bar
//		xpBar = new XPBar(Handler.get(), hpBar.getX() + hpBar.getWidth(), y);
		
		abilityTooltip = new AbilityTooltip(0, Handler.get().getHeight() / 2, 160, 224);

//		this.width = x + xpBar.getX() + xpBar.getWidth();
//		this.height = y + ItemSlot.SLOTSIZE;
	}
	
	/**
	 * Handles the pressed ability slot button
	 */
	private void handleKeyEvent() {
		// Get the right index in the ability slots
		// Funky calculation. If 0 is pressed, it should be the last slot instead of first, otherwise the slot is 1-9 pressed -1 by index
		Ability selectedAbility = slottedAbilities.get(pressedKey == 48 ? slottedAbilities.size()-1 : (pressedKey-49)).getAbility();
		if(selectedAbility != null) {
			for(AbilitySlot as : slottedAbilities) {
				if(as.getAbility() != null) {
					if(as.getAbility().isChanneling()) {
						return;
					}else if(as.getAbility().isSelectable() && as.getAbility().isSelected()){
						as.getAbility().setSelected(false);
						as.getAbility().setActivated(false);
					}
				}
			}
			if(selectedAbility.isOnCooldown()) {
				Handler.get().sendMsg(selectedAbility.getName() + " is on cooldown.");
				return;
			}else {
				if(selectedAbility.isSelectable()) {
					selectedAbility.setSelected(true);
				}
				if(!Handler.get().getAbilityManager().getActiveAbilities().contains(selectedAbility)) {
					Handler.get().getAbilityManager().getActiveAbilities().add(selectedAbility);
					selectedAbility.setCaster(Handler.get().getPlayer());
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
				Handler.get().sendMsg(selectedAbility.getName() + " is on cooldown.");
				return;
			}else {
				if(selectedAbility.isSelectable()) {
					selectedAbility.setSelected(true);
				}
				if(!Handler.get().getAbilityManager().getActiveAbilities().contains(selectedAbility)) {
					Handler.get().getAbilityManager().getActiveAbilities().add(selectedAbility);
					selectedAbility.setCaster(Handler.get().getPlayer());
				}
			}
		}
	}
	
	public void tick() {
		Rectangle mouse = Handler.get().getMouse();

		// Check for input
		if(hasBeenTyped) {
			handleKeyEvent();
			hasBeenTyped = false;
		}
		
		// Tick the tooltip and other bars
		for(AbilitySlot as : slottedAbilities) {
			as.tick();
			if(as.getBounds().contains(mouse)){
				abilityTooltip.tick();
				if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					handleClickEvent(as);
					hasBeenPressed = false;
				}
			}
		}
//		hpBar.tick();
//		xpBar.tick();
	}
	
	public void render(Graphics g) {
		Rectangle mouse = Handler.get().getMouse();
		
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
