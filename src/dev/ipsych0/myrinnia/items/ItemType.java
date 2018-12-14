package dev.ipsych0.myrinnia.items;

import java.io.Serializable;

public enum ItemType implements Serializable {
    ARMOUR, ACCESSORY, MELEE_WEAPON, RANGED_WEAPON, MAGIC_WEAPON,
    CRAFTING_MATERIAL, CURRENCY, QUEST_ITEM, UPGRADE_COMPONENT, POTION, FOOD, AXE, PICKAXE;

    private String name;

    ItemType(/*String name*/) {
        //this.name = name;
        // Constructor voor ItemTypes hier in de class !!!!!!!!!!!!!!!
    }

    public String getString() {
        return name;
    }
}
