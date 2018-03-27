package dev.ipsych0.mygame.items;

import java.io.Serializable;

public enum ItemType implements Serializable{
	LIGHT_ARMOUR, MEDIUM_ARMOUR, HEAVY_ARMOUR, CRAFTING_MATERIAL, MELEE_WEAPON, RANGED_WEAPON, MAGIC_WEAPON,
	CURRENCY, QUEST_ITEM, UPGRADE_COMPONENT, POTION, FOOD, AXE, PICKAXE;
	
	private String test;
	
	ItemType() {
		// Constructor voor ItemTypes hier in de class !!!!!!!!!!!!!!!
	}
	
	public String getString() {
		return test;
	}
}
