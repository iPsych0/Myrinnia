package dev.ipsych0.mygame.items;

public class EquipmentStack {
	
	private Item item;
	
	public EquipmentStack(Item item){
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
