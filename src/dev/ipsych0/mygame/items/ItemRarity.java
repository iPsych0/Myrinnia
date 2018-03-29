package dev.ipsych0.mygame.items;

import java.awt.Color;
import java.io.Serializable;

public enum ItemRarity implements Serializable {
	Common, Uncommon, Rare, Exquisite, Unique;
	
	public static Color getColor(Item item) {
		if(item.itemRarity == Common) {
			return Color.WHITE;
		}
		else if(item.itemRarity == Uncommon) {
			return Color.BLUE;
		}
		else if(item.itemRarity == Rare) {
			return Color.ORANGE;
		}
		else if(item.itemRarity == Exquisite) {
			return Color.GREEN;
		}
		else if(item.itemRarity == Unique) {
			return Color.MAGENTA;
		}
		else {
			return Color.WHITE;
		}
	}
}
