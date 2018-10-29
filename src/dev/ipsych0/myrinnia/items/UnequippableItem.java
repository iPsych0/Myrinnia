package dev.ipsych0.myrinnia.items;

import java.awt.image.BufferedImage;

public class UnequippableItem extends Item{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6587812868071560557L;

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
