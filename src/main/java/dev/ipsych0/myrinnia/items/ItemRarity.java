package dev.ipsych0.myrinnia.items;

import java.awt.*;
import java.io.Serializable;

public enum ItemRarity implements Serializable {
    Common, Uncommon, Rare, Exquisite, Unique;

    private static Color exquisite = new Color(27, 171, 12);

    public static Color getColor(Item item) {
        if (item.getItemRarity() == Common) {
            return Color.WHITE;
        } else if (item.getItemRarity() == Uncommon) {
            return Color.CYAN;
        } else if (item.getItemRarity() == Rare) {
            return Color.ORANGE;
        } else if (item.getItemRarity() == Exquisite) {
            return exquisite;
        } else if (item.getItemRarity() == Unique) {
            return Color.MAGENTA;
        } else {
            return Color.WHITE;
        }
    }
}
