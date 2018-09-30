package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

public class NimbleFeetAbility extends Ability {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6275665878660555382L;
	private float baseMovementBoost = 1.0f;
	private int boostTime = 20 * 60;
	private int boostTimeTimer = 0;
	private boolean initialBoostDone = false;

	public NimbleFeetAbility(CharacterStats element, String name, AbilityType abilityType, boolean selectable,
			int cooldownTime, int castingTime, int overcastTime, int baseDamage, String description) {
		super(element, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);

		price = 2;
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(Assets.bootsSlot, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
	}

	@Override
	public void cast() {
		if(!initialBoostDone) {
			getCaster().setSpeed(getCaster().getSpeed() + baseMovementBoost);
			initialBoostDone = true;
			Handler.debugMode = true;
		}
		boostTimeTimer++;
		if(boostTimeTimer >= boostTime) {
			if(getCaster().getSpeed() - baseMovementBoost < Creature.DEFAULT_SPEED+3.0f) 
				getCaster().setSpeed(Creature.DEFAULT_SPEED+3.0f);
			else
				getCaster().setSpeed(getCaster().getSpeed() - baseMovementBoost);
			
			setCasting(false);
		}
		
	}
	
	@Override
	protected void countDown() {
		cooldownTimer++;
		if(cooldownTimer / 60 == cooldownTime) {
			this.setOnCooldown(false);
			this.setActivated(false);
			this.setCasting(false);
			castingTimeTimer = 0;
			cooldownTimer = 0;
			initialBoostDone = false;
			boostTimeTimer = 0;
		}
	}

}
