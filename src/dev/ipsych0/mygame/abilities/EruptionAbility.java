package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

public class EruptionAbility extends Ability {
	
	public EruptionAbility(Handler handler, CharacterStats element, String name, AbilityType abilityType, boolean selectable,
			int cooldownTime, int castingTime, int overcastTime, int baseDamage, String description) {
		super(handler, element, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(Assets.dirtHole, x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

}
