package dev.ipsych0.mygame.items;

import java.io.Serializable;

public class EquipmentStack implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Item item;
	private int amount;
	
	public EquipmentStack(Item item){
		this.item = item;
		this.amount = 1;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
