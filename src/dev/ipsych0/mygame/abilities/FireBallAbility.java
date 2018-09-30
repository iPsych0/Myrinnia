package dev.ipsych0.mygame.abilities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.entities.creatures.Creature;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

public class FireBallAbility extends Ability {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -2623074711686137258L;

	public FireBallAbility(CharacterStats element, String name, AbilityType abilityType, boolean selectable,
			int cooldownTime, int castingTime, int overcastTime, int baseDamage, String description) {
		super(element, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(Assets.magicProjectile[2], x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);
		
	}

	@Override
	public void cast() {
		Handler.get().getMouseManager().setLeftPressed(true);
		Handler.get().getPlayer().checkMagic(new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1));
		Handler.get().getMouseManager().setLeftPressed(false);
		setCasting(false);
	}
	
	@Override
	public void setCaster(Creature c) {
		this.caster = c;
		this.setActivated(true);
		System.out.println("Cast: "+this.getName());
	}

}
