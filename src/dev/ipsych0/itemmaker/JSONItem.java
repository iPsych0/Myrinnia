package dev.ipsych0.itemmaker;

import dev.ipsych0.myrinnia.items.EquipSlot;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.items.ItemRequirement;
import dev.ipsych0.myrinnia.items.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

class JSONItem implements Serializable {

    private static final long serialVersionUID = -758455032925771207L;
    ItemType[] itemTypes;
    ItemRarity itemRarity;
    ItemRequirement[] requirements;
    BufferedImage texture;
    String name;
    final int id;
    EquipSlot equipSlot;
    int power;
    int defence;
    int vitality;
    float attackSpeed;
    float movementSpeed;
    int x;
    int y;
    Rectangle bounds;
    Rectangle position;
    protected int count;
    boolean pickedUp = false;
    static boolean pickUpKeyPressed = false;
    int price;
    boolean stackable;
    int respawnTimer = 10800;

    public JSONItem(BufferedImage texture, String name, ItemRarity itemRarity,
                    EquipSlot equipSlot, int power, int defence, int vitality, float attackSpeed, float movementSpeed,
                    int price, boolean stackable, ItemType[] itemTypes, ItemRequirement... requirements) {

        this.texture = texture;
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.id = IDGenerator.getInstance().getNextId();
        this.itemRarity = itemRarity;
        this.price = price;
        this.stackable = stackable;
        this.itemTypes = itemTypes;
        this.requirements = requirements;
        this.equipSlot = equipSlot;
        this.power = power;
        this.defence = defence;
        this.vitality = vitality;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;

        bounds = new Rectangle(0, 0, 32, 32);
        position = new Rectangle(0, 0, 32, 32);
    }
}
