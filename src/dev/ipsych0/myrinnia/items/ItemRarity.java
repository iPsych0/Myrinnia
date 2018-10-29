package dev.ipsych0.myrinnia.items;

import java.awt.Color;
import java.io.Serializable;

public enum ItemRarity implements Serializable {
	Common, Uncommon, Rare, Exquisite, Unique;
	
	public static Color getColor(Item item) {
		if(item.getItemRarity() == Common) {
			return Color.WHITE;
		}
		else if(item.getItemRarity() == Uncommon) {
			return Color.BLUE;
		}
		else if(item.getItemRarity() == Rare) {
			return Color.ORANGE;
		}
		else if(item.getItemRarity() == Exquisite) {
			return Color.GREEN;
		}
		else if(item.getItemRarity() == Unique) {
			return Color.MAGENTA;
		}
		else {
			return Color.WHITE;
		}
	}
}
