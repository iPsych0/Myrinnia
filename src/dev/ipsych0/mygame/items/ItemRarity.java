package dev.ipsych0.mygame.items;

import java.awt.Color;

public enum ItemRarity {
	Common, Uncommon, Rare, Exquisite, Unique;
	
	public static Color getColor(Item item) {
		if(item.itemRarity == Common) {
			return Color.WHITE;
		}
		if(item.itemRarity == Uncommon) {
			return Color.BLUE;
		}
		if(item.itemRarity == Rare) {
			return Color.ORANGE;
		}
		if(item.itemRarity == Exquisite) {
			return Color.GREEN;
		}
		if(item.itemRarity == Unique) {
			return Color.MAGENTA;
		}
		return Color.WHITE;
	}
}
