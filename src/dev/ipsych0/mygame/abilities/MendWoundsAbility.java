package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;

import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

public class MendWoundsAbility extends Ability{

	public MendWoundsAbility(CharacterStats element, String name, AbilityType abilityType, int cooldownTimer,
			int castingTime, int overcastTime, int baseDamage, String description) {
		super(element, name, abilityType, cooldownTimer, castingTime, overcastTime, baseDamage, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(Assets.waterFlow1, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

}
