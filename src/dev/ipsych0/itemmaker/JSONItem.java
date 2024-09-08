package dev.ipsych0.itemmaker;

import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.items.ItemRequirement;
import dev.ipsych0.myrinnia.items.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

class JSONItem implements Serializable {

    private static final long serialVersionUID = -758455032925771207L;
    private ItemType[] itemTypes;
    private ItemRarity itemRarity;
    private ItemRequirement[] requirements;
    private BufferedImage texture;
    private String name;
    final int id;
    private EquipSlot equipSlot;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int defence;
    private int vitality;
    private float attackSpeed;
    private float movementSpeed;
    private int x;
    private int y;
    private Rectangle bounds;
    private Rectangle position;
    private int count;
    private boolean pickedUp = false;
    private static boolean pickUpKeyPressed = false;
    private int price;
    private boolean stackable;
    private boolean equippable;
    private int respawnTimer = 10800;
    private int useCooldown = 60;

    public JSONItem(BufferedImage texture, String name, ItemRarity itemRarity,
                    EquipSlot equipSlot, int strength, int dexterity, int intelligence, int defence, int vitality, float attackSpeed, float movementSpeed,
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
        this.equippable = (equipSlot != EquipSlot.None);
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.defence = defence;
        this.vitality = vitality;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;

        bounds = new Rectangle(0, 0, 32, 32);
        position = new Rectangle(0, 0, 32, 32);
    }
}
