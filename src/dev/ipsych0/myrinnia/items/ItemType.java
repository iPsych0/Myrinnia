package dev.ipsych0.myrinnia.items;

import java.io.Serializable;

public enum ItemType implements Serializable{
	Armour, Accessory, Melee_Weapon, Ranged_Weapon, Magic_Weapon,
	Crafting_Material, Currency, Quest_Item, Upgrade_Component, Potion, Food, Axe, Pickaxe;
	
	private String name;
	
	ItemType(/*String name*/) {
		//this.name = name;
		// Constructor voor ItemTypes hier in de class !!!!!!!!!!!!!!!
	}
	
	public String getString() {
		return name;
	}
}
