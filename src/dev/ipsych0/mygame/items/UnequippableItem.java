package dev.ipsych0.mygame.items;

import java.awt.image.BufferedImage;

public class UnequippableItem extends Item{

	public UnequippableItem(BufferedImage texture, String name, int id, ItemRarity itemRarity, int price,
			boolean isStackable, ItemType... itemTypes) {
		super(texture, name, id, itemRarity, price, isStackable, itemTypes);
		
		this.equipSlot = EquipSlot.NONE;
		this.power = 0;
		this.defence = 0;
		this.vitality = 0;
		this.attackSpeed = 0;
		this.movementSpeed = 0;
	}

}
