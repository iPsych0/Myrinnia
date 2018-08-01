package dev.ipsych0.mygame.abilityhud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.AbilityType;
import dev.ipsych0.mygame.abilities.EruptionAbility;
import dev.ipsych0.mygame.abilities.FireBallAbility;
import dev.ipsych0.mygame.abilities.MendWoundsAbility;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.utils.Text;

public class PlayerHUD {
	
	private Handler handler;
	private List<AbilitySlot> slottedAbilities = new ArrayList<AbilitySlot>();
	private static final int MAX_SLOTS = 10;
	private HPBar hpBar;
	private XPBar xpBar;
	private int x, y, width, height;
	private AbilityTooltip abilityTooltip;
	
	public PlayerHUD(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < MAX_SLOTS; i++) {
			slottedAbilities.add(new AbilitySlot(handler, null, x + (i * 32), y));
		}
		slottedAbilities.get(0).setAbility(new FireBallAbility(CharacterStats.Fire, "Fireball", AbilityType.AutoAttack, 0,0,0,10, "A weak fireball spell."));
		slottedAbilities.get(1).setAbility(new EruptionAbility(CharacterStats.Fire, "Eruption", AbilityType.StandardAbility, 10,0,0,25, "Causes an eruption that deals X AoE damage."));
		slottedAbilities.get(2).setAbility(new MendWoundsAbility(CharacterStats.Water, "Mend Wounds", AbilityType.HealingAbility, 25,0,0,0, "Heal yourself for X amount of health and gain regeneration for Y seconds."));
		
		// Add HP Bar after the last abilitySlot
		hpBar = new HPBar(handler, slottedAbilities.get(slottedAbilities.size()-1).getX() + ItemSlot.SLOTSIZE, y);
		// Add XP Bar after HP Bar
		xpBar = new XPBar(handler, hpBar.getX() + hpBar.getWidth(), y);
		
		abilityTooltip = new AbilityTooltip(0, handler.getHeight() / 2, 160, 224);
		
		this.width = x + xpBar.getX() + xpBar.getWidth();
		this.height = y + ItemSlot.SLOTSIZE;
	}
	
	public void tick() {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		for(AbilitySlot as : slottedAbilities) {
			as.tick();
			if(as.getBounds().contains(mouse)){
				abilityTooltip.tick();
			}
		}
		hpBar.tick();
		xpBar.tick();
	}
	
	public void render(Graphics g) {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		for(AbilitySlot as : slottedAbilities) {
			as.render(g);
			if(as.getBounds().contains(mouse)){
				if(as.getAbility() != null) {
					abilityTooltip.render(g, as.getAbility());
				}
			}
		}
		hpBar.render(g);
		xpBar.render(g);
	}
	
}
