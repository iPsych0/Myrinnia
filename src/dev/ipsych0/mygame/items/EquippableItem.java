package dev.ipsych0.mygame.items;

import java.awt.image.BufferedImage;

public class EquippableItem extends Item{

	/**
	 * Constructor for equippable Items WITH requirements to wield
	 * @param texture
	 * @param name
	 * @param id
	 * @param itemRarity
	 * @param equipSlot
	 * @param power
	 * @param defence
	 * @param vitality
	 * @param attackSpeed
	 * @param movementSpeed
	 * @param price
	 * @param isStackable
	 * @param itemTypes
	 * @param requirements
	 */
	public EquippableItem(BufferedImage texture, String name, int id, ItemRarity itemRarity,
			EquipSlot equipSlot, int power, int defence, int vitality, float attackSpeed, float movementSpeed,
			int price, boolean isStackable, ItemType[] itemTypes, ItemRequirement... requirements) {
		
		super(texture, name, id, itemRarity, price, isStackable, itemTypes);
		
		this.requirements = requirements;
		this.equipSlot = equipSlot;
		this.power = power;
		this.defence = defence;
		this.vitality = vitality;
		this.attackSpeed = attackSpeed;
		this.movementSpeed = movementSpeed;
		
	}
	
	/**
	 * Constructor for equippable items that have NO requirements.
	 * @param texture
	 * @param name
	 * @param id
	 * @param itemRarity
	 * @param equipSlot
	 * @param power
	 * @param defence
	 * @param vitality
	 * @param attackSpeed
	 * @param movementSpeed
	 * @param price
	 * @param isStackable
	 * @param itemTypes
	 */
	public EquippableItem(BufferedImage texture, String name, int id, ItemRarity itemRarity,
			EquipSlot equipSlot, int power, int defence, int vitality, float attackSpeed, float movementSpeed,
			int price, boolean isStackable, ItemType[] itemTypes) {
		
		super(texture, name, id, itemRarity, price, isStackable, itemTypes);
		
		this.requirements = null;
		this.equipSlot = equipSlot;
		this.power = power;
		this.defence = defence;
		this.vitality = vitality;
		this.attackSpeed = attackSpeed;
		this.movementSpeed = movementSpeed;
		
	}

	

}
