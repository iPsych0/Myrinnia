package dev.ipsych0.mygame.items;

import java.awt.image.BufferedImage;

public class EquippableItem extends Item{

	public EquippableItem(BufferedImage texture, String name, int id, ItemRarity itemRarity,
			EquipSlot equipSlot, int power, int defence, int vitality, float attackSpeed, float movementSpeed,
			int price, boolean isStackable, ItemType... itemTypes) {
		
		super(texture, name, id, itemRarity, price, isStackable, itemTypes);
		
		this.equipSlot = equipSlot;
		this.power = power;
		this.defence = defence;
		this.vitality = vitality;
		this.attackSpeed = attackSpeed;
		this.movementSpeed = movementSpeed;
	}

	

}
