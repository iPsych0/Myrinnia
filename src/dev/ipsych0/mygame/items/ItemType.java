package dev.ipsych0.mygame.items;

import java.io.Serializable;

public enum ItemType implements Serializable{
	MELEE_ARMOUR, RANGED_ARMOUR, MAGIC_ARMOUR, HYBRID_ARMOUR, MELEE_WEAPON, RANGED_WEAPON, MAGIC_WEAPON,
	CRAFTING_MATERIAL, CURRENCY, QUEST_ITEM, UPGRADE_COMPONENT, POTION, FOOD, TOOL;
	
	private String test;
	
	ItemType() {
		// Constructor voor ItemTypes hier in de class !!!!!!!!!!!!!!!
	}
	
	public String getString() {
		return test;
	}
}
