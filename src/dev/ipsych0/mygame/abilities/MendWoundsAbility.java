package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

public class MendWoundsAbility extends Ability{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7613939541331724237L;
	private int regenTimer = 0;
	private int regenSeconds = 5 * 60;
	private int baseHeal = 30;
	private int regenHeal = 3;
	private boolean initialHealDone = false;

	public MendWoundsAbility(CharacterStats element, String name, AbilityType abilityType, boolean selectable,
			int cooldownTime, int castingTime, int overcastTime, int baseDamage, String description) {
		super(element, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(Assets.waterFlow1, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
	}

	@Override
	public void cast() {
		// Heal to max HP if current HP + baseHeal >= max HP already, otherwise add baseHeal
		if(!initialHealDone) {
			int heal = 0;
			this.caster.setHealth(heal = (this.caster.getHealth() + baseHeal >= this.caster.getMaxHealth()) ?
					this.caster.getMaxHealth() : (this.caster.getHealth() + baseHeal));
			initialHealDone = true;
		}
		
		// Regen
		regenTimer++;
		if(regenTimer % 60 == 0) {
			// When we've reached the regen timer limit, the spell is done, set casting to false.
			if(regenTimer == regenSeconds) {
				this.setCasting(false);
			}
			int regen = 0;
			this.caster.setHealth(regen = (this.caster.getHealth() + regenHeal >= this.caster.getMaxHealth()) ?
					this.caster.getMaxHealth() : (this.caster.getHealth() + regenHeal));
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
			regenTimer = 0;
			cooldownTimer = 0;
			initialHealDone = false;
		}
	}

}
