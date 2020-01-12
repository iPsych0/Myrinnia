package dev.ipsych0.myrinnia.items;

import java.io.Serializable;

public enum ItemType implements Serializable {
    ARMOUR, ACCESSORY, MELEE_WEAPON, RANGED_WEAPON, MAGIC_WEAPON,
    CRAFTING_MATERIAL, QUEST_ITEM, POTION, FOOD, AXE, PICKAXE, FISHING_ROD, MISC, SEED;

    private String name;

    ItemType(/*String name*/) {
        //this.name = name;
        // Constructor voor ItemTypes hier in de class !!!!!!!!!!!!!!!
    }

    public String getString() {
        return name;
    }
}
