package dev.ipsych0.mygame.abilityhud;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.AbilityType;
import dev.ipsych0.mygame.abilities.FireBallAbility;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.items.ItemSlot;

public class AbilityUI {
	
	private Handler handler;
	private List<AbilitySlot> slottedAbilities = new ArrayList<AbilitySlot>();
	private static final int MAX_SLOTS = 6;
	private HPBar hpBar;
	private XPBar xpBar;
	private int x, y, width, height;
	
	public AbilityUI(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < MAX_SLOTS; i++) {
			slottedAbilities.add(new AbilitySlot(handler, null, x + (i * 32), y));
		}
		slottedAbilities.get(0).setAbility(new FireBallAbility(CharacterStats.Fire, "Fireball", AbilityType.AutoAttack, 0,0,0,0, ""));
		
		// Add HP Bar after the last abilitySlot
		hpBar = new HPBar(handler, slottedAbilities.get(slottedAbilities.size()-1).getX() + ItemSlot.SLOTSIZE, y);
		// Add XP Bar after HP Bar
		xpBar = new XPBar(handler, hpBar.getX() + hpBar.getWidth(), y);
		
		this.width = x + xpBar.getX() + xpBar.getWidth();
		this.height = y + ItemSlot.SLOTSIZE;
	}
	
	public void tick() {
		for(AbilitySlot as : slottedAbilities) {
			as.tick();
		}
		hpBar.tick();
		xpBar.tick();
	}
	
	public void render(Graphics g) {
		for(AbilitySlot as : slottedAbilities) {
			as.render(g);
		}
		hpBar.render(g);
		xpBar.render(g);
	}
	
}
